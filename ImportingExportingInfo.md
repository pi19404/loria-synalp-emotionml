

# Info elements #

One interesting feature about EmotionML is that it is possible to embed external information inside an emotion, a vocabulary, a vocabulary item or a whole document. This external information is represented as an `<info>` element that can contain any kind of data as long as it is not EmotionML.

The following example shows an emotionml document whose `<info>` element holds non-EmotionML information, namely a classifier information. This document also contains two emotions whose `<info>` elements contain non-EmotionML information related to localization:
```
<emotionml xmlns="http://www.w3.org/2009/10/emotionml"
        xmlns:classifiers="http://www.example.com/meta/classify/"
        xmlns:origin="http://www.example.com/meta/local/"
        category-set="http://www.w3.org/TR/emotion-voc/xml#big6"
        version="1.0">
    <info>
        <classifiers:classifier classifiers:name="GMM"/>
    </info>

    <emotion>
        <info><origin:localization value="bavarian"/></info>
        <category name="happiness"/>
    </emotion>

    <emotion>
        <info><origin:localization value="swabian"/></info>
        <category name="sadness"/>
    </emotion>
</emotionml>
```

In order to export/import the info elements, it is required to tell explicitly how they are to be serialized/deserialized. There is no restriction on how to do that, one can use any kind of tool or method. The only requirements imposed by EmotionML is that the `<info>` elements do not contain EmotionML elements, and that any element in it has a different namespace than the default EmotionML namespace ("http://www.w3.org/2009/10/emotionml"). If XML is not needed to represent the information, one can also serialize it as plain text inside the `<info>` element. We present here two possible ways to do that import/export, using DOM or using JAXB.

# Using DOM #

One possible way to export/import Info elements is to use DOM, by building manually the output XML tree, or by traversing the input one.

## Representation ##

To work with such Info elements in Java, one would first need to subclass the Info class. For instance, the classifier info could be represented as the following minimal class:

```
class ClassifierInfo extends Info
{
    String name;
    static String namespace = "http://www.example.com/meta/classify/";

    ClassifierInfo(String name)
    {   
        this.name = name; 
    }   
}
```

While the localization can also subclass the Info class:
```
class LocalizationInfo extends Info
{
    String value;
    static String namespace = "http://www.example.com/meta/local/";

    LocalizationInfo(String value)
    {   
        this.value = value; 
    }   
}
```

One can then build an emotion or document and call the `setInfo` method:
```
Emotion emotion = new Emotion(Big6.HAPPINESS).setInfo(new LocalizationInfo("bavarian"));
```


## Exporting ##

In the following example we show how the `ClassifierInfo` and the `LocalizationInfo` could be serialized using DOM in a way similar to the XML example above. The idea is to subclass the `EmotionMLExporter` and override the `exportInfo` method. This method takes in input an `Info` and a Document instance, the document instance is only needed to create the new DOM elements that will contain our information:

```
class CustomExporter extends EmotionMLExporter
{
    @Override
    public Element exportInfo(Info info, Document doc)
    {   
        Element ret = super.exportInfo(info, doc);

        if (info instanceof ClassifierInfo)
            ret.appendChild(export((ClassifierInfo) info, doc));

        if (info instanceof LocalizationInfo)
            ret.appendChild(export((LocalizationInfo) info, doc));

        return ret;
    }   


    private Element export(LocalizationInfo info, Document doc)
    {   
        Element ret = doc.createElementNS(LocalizationInfo.namespace, "localization");
        ret.setAttribute("value", info.value);
        return ret;
    }   


    private Element export(ClassifierInfo info, Document doc)
    {   
        Element ret = doc.createElementNS(ClassifierInfo.namespace, "classifier");
        ret.setAttribute("name", info.name);
        return ret;
    }   
}
```

This exporter first creates an Element using the `super.exportInfo` method which will return an element `<info>` ready to be augmented by the particular content of the `Info`. Then it tests the class of the given `Info` instance, and calls a different method for `LocalizationInfo` or `ClassifierInfo`. These methods are pretty similar, they just create a new element `<localization>` or `<classifier>` with the appropriate namespace and sets the attributes "value" or "name" to their desired value. The newly created element is finally added to the `<info>` element.

When exporting our emotion, it would return the result:
```
<emotion category-set="http://www.w3.org/TR/emotion-voc/xml#big6">
    <info>
        <localization value="bavarian" xmlns="http://www.example.com/meta/local/"/>
    </info>
    <category name="happiness"/>
</emotion>
```

Note that it is not perfect, we could have a prefix "origin" like in the example.


## Importing ##

Importing works the same way than exporting by subclassing the `EmotionMLImporter` class and overriding the `importInfo` method.

The following example shows a quite careless way to import a custom info element: it goes through the children of the given `<info>` element, does not care about the namespace or potential errors, and builds instances of `ClassifierInfo` or `LocalizationInfo` with the attributes values:
```
class CustomImporter extends EmotionMLImporter
{
    @Override
    public Info importInfo(Element element)
    {   
        NodeList children = element.getChildNodes();
        for(int i=0; i<children.getLength(); i++)
        {       
            Node child = children.item(i);
            if (child instanceof Element)
            {           
                Element e = (Element) child;
                String name = e.getLocalName();
                if (name.equals("classifier"))
                    return new ClassifierInfo(e.getAttribute("name"));
                else if (name.equals("localization"))
                    return new LocalizationInfo(e.getAttribute("value"));
            }           
        }       
        
        return super.importInfo(element);
    }   
} 
```

# Using JAXB #

Building the importer and exporter classes using DOM can be a tedious task. It is also possible to use JAXB to import and export Info objects. The idea is to annotate the Info classes with JAXB annotations that define how the fields have to be serialized. There are at least two constraints on the Info classes:
  * they need to contain an empty constructor
  * they need to contain a `@XmlRootElement` annotation that defines a namespace different from the EmotionML namespace

Our classes would then be:
```
@XmlRootElement(name="classifier", namespace="http://www.example.com/meta/classify/")
class ClassifierInfo extends Info
{
    @XmlAttribute
    String name;

    ClassifierInfo() {}

    ClassifierInfo(String name)
    {   
        this.name = name; 
    }   
}
```

and:
```
@XmlRootElement(name="localization", namespace="http://www.example.com/meta/local/")
class LocalizationInfo extends Info
{
    @XmlAttribute
    String value;

    LocalizationInfo() {}

    LocalizationInfo(String value)
    {   
        this.value = value; 
    }   
}
```

Once the classes have be defined like this, exporting the emotion is quite simple and relies on a `JaxbEmotionMLExporter` instance:
```
Emotion emotion = new Emotion(Big6.HAPPINESS).setInfo(new LocalizationInfo("bavarian"));

new JaxbEmotionMLExporter().export(emotion, System.out);
```

which will produce the same output than previously:
```
<emotion category-set="http://www.w3.org/TR/emotion-voc/xml#big6">
    <info>
        <localization value="bavarian" xmlns="http://www.example.com/meta/local/"/>
    </info>
    <category name="happiness"/>
</emotion>
```

Importing is slightly more complex than exporting since JAXB requires to know to which classes the XML elements will be unmarshalled. To do the import, an instance of class `JaxbEmotionMLImporter` needs to be created, and the method `addInfoClasses` needs to be called with each of the Info classes we want to unmarshall to:

```
JaxbEmotionMLImporter importer = new JaxbEmotionMLImporter();
importer.addInfoClasses(ClassifierInfo.class, LocalizationInfo.class);
```

Once done, the Jaxb importer works the same way than other importers.