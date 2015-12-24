# Exporting emotions #

We show here some basic examples on how to build and export emotions. These examples can be found in the [fr.loria.synalp.emotionml.examples](https://loria-synalp-emotionml.googlecode.com/svn/trunk/doc/index.html?fr/loria/synalp/emotionml/examples/package-summary.html) package.



## Example 1 - Basic emotion ##

This example shows how to build an emotion using a predefined vocabulary of categories called [Big6](https://loria-synalp-emotionml.googlecode.com/svn/trunk/doc/index.html?fr/loria/synalp/emotionml/vocabularies/predefined/Big6.html). When adding such predefined category, the advantage is that the category-set of the emotion is automatically setup. It would not be the case when building an emotion with a custom name.
```
Emotion emotion = new Emotion(Big6.FEAR);
new EmotionMLExporter().export(emotion, System.out);
```

It produces:
```
<?xml version="1.0" encoding="UTF-8"?>
<emotion xmlns="http://www.w3.org/2009/10/emotionml"
         category-set="http://www.w3.org/TR/emotion-voc/xml#big6">
    <category name="fear"/>
</emotion>
```


## Example 2 - Compound emotion ##

This example shows that it is possible to combine a category like `Big6.FEAR` and a dimension like `FsreDimensions.AROUSAL`. By default predefined dimensions have a value of 1.
```
Emotion emotion = new Emotion(Big6.FEAR, FsreDimensions.AROUSAL);
new EmotionMLExporter().export(emotion, System.out);
```

It produces:
```
<?xml version="1.0" encoding="UTF-8"?>
<emotion xmlns="http://www.w3.org/2009/10/emotionml"
         category-set="http://www.w3.org/TR/emotion-voc/xml#big6" 
         dimension-set="http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions">
    <category name="fear"/>
    <dimension name="arousal" value="1.0"/>
</emotion>
```


## Example 3 - We cannot have two categories from two vocabularies ##

However it is not possible to mix two categories from two different vocabularies. For instance, when trying to export an emotion with `Big6.FEAR` and `EverydayCategories.AFRAID`, the exporter throws an exception.
```
Emotion emotion = new Emotion(Big6.FEAR, EverydayCategories.AFRAID);
new EmotionMLExporter().export(emotion, System.out); // exception thrown because Big6.FEAR has no category-set
```

It produces the rather lengthy error message:
```
fr.loria.synalp.emotionml.exceptions.EmotionMLValidationException: schema validated; 
assertion invalidated: 212: The value of the "name" attribute of the <category> element MUST be contained in the declared 
category vocabulary (the name is "fear" but the declared names for 
"http://www.w3.org/TR/emotion-voc/xml#everyday-categories" are [satisfied, sad, bored, angry, excited, amused, loving, 
interested, disappointed, content, afraid, worried, relaxed, confident, happy, affectionate, pleased])
```

Note that what really happens is that first `Big6.FEAR` is added to the emotion and the category-set URI of the emotion is setup to refer to the `Big6` vocabulary, then `EveryDayCategories.AFRAID` is added, and the category-set URI is replaced such that it refers now to the `EveryDayCategories` vocabulary. But when exporting, the `Big6.FEAR` category has no more category-set URI and the exception is thrown.

The exception states that the produced XML output satisfies the schema constraints (see [EmotionML schema](http://www.w3.org/TR/emotionml/#schema)) but does not satisfy the assertion 212 (see [EmotionML assertions](http://www.w3.org/2002/mmi/2013/emotionml-ir/#test_class)).


## Example 4 - A custom category needs a category-set ##

So now if we want to create a custom category, for instance "love", we have to take care of the category-set URI, otherwise an exception is thrown as in the following example:
```
Emotion emotion = new Emotion(new Category("love"));
new EmotionMLExporter().export(emotion, System.out); // exception thrown, category "love" has no category-set
```

It produces the given error message:
```
fr.loria.synalp.emotionml.exceptions.EmotionMLValidationException: schema validated; 
assertion invalidated: 210: If the <category> element is used, a category vocabulary MUST be declared using a 
"category-set" attribute on either the enclosing <emotion> element or the root element <emotionml> (the <emotion> has 
none and has no <emotionml> parent)
```

## Example 5 - A category-set needs to refer to a vocabulary ##

We can set the category-set manually by calling the `setCategorySetURI` method, however this URI needs to refer to a valid vocabulary as defined EmotionML (see [Defining vocabularies for representing emotions](http://www.w3.org/TR/emotionml/#s3)). The vocabulary needs to meet some additional constraints, it needs to be accessible online, needs to be a vocabulary of type "category" and needs to contain our category name, namely "love". Providing a fake URI such as http://myvocabularies#mycategories" will thus fail and throw an exception when exporting as in the following example:

```
Emotion emotion = new Emotion(new Category("love"));
emotion.setCategorySetURI("http://myvocabularies#mycategories");
new EmotionMLExporter().export(emotion, System.out); // exception thrown because the URI cannot be resolved
```

It produces the to-be-improved given error message:

```
fr.loria.synalp.emotionml.exceptions.EmotionMLValidationException: schema validated; 
assertion invalidated: Unable to retrieve vocabulary from URI <http://myvocabularies#mycategories>, check the URL 
since there has been an IO exception while opening the stream: myvocabularies
```

## Example 6 - We can bypass the vocabulary resolution ##

Well, this mechanism for category-set URIs (and others such as dimension-set, appraisal-set and action-tendency-set) can be a blocking factor when developing offline. It is thus possible to deactivate the vocabulary resolution when exporting an emotion. When doing the export of an emotion, the exporter performs a validation step using an instance of class EmotionMLValidator. By default this class always tries to resolve vocabularies. To deactivate vocabulary resolution we then need to pass an EmotionMLValidator to the EmotionMLExporter constructor that does not care about the vocabulary resolution, like in the following example. Note that when doing so, the exporter produces then **invalid** emotions with regards to EmotionML.

```
Emotion emotion = new Emotion(new Category("love"));
emotion.setCategorySetURI("http://myvocabularies#mycategories");

EmotionMLExporter exporter = new EmotionMLExporter(new EmotionMLValidator().setResolveVocabularies(false));
exporter.export(emotion, System.out); // no more exception
```

It produces the **invalid** output:
```
<?xml version="1.0" encoding="UTF-8"?>
<emotion category-set="http://myvocabularies#mycategories" xmlns="http://www.w3.org/2009/10/emotionml">
    <category name="love"/>
</emotion>
```


## Example 7 - Or we can create a vocabulary document ##

What if we really want to build a "love" category and produce valid emotions ? We have to also provide a vocabulary that describes the "love" category. In order to do so we can create the vocabulary document manually (see for example [EmotionML vocabularies](http://www.w3.org/TR/emotion-voc/)), or we can create it by code. The following examples first creates a vocabulary with id "mycategories" of type CATEGORY, and adds two items to it "love" and "hate. The vocabulary is then added to an EmotionMLDocument which is exported to a temporary file. When creating a "love" category, the category-set URI is defined to refer to that file, and to the "mycategories" vocabulary inside it. To refer to a particular vocabulary, EmotionML standard uses the fragment of the URI as the id of the vocabulary (`base`#`fragment`). Since the exporter can now find a vocabulary at that location, no exception is thrown when exporting.

```
// create vocabulary
String myVocabularyId = "mycategories";
Vocabulary vocabulary = new Vocabulary(myVocabularyId, VocabularyType.CATEGORY);
vocabulary.addItem("love");
vocabulary.addItem("hate");

// add it to a new doc  
EmotionMLDocument doc = new EmotionMLDocument();
doc.add(vocabulary);    

// and write the doc    
File file = File.createTempFile("myvocabularies", ".xml");
exporter.export(doc, file);

// we could now safely refer to the love Category
Emotion emotion = new Emotion(new Category("love"));
emotion.setCategorySetURI(file.toURI() + "#" + myVocabularyId);

new EmotionMLExporter().export(emotion, System.out); // no exception but it would be better to save the doc somewhere else
```

The vocabulary document looks like this:
```
<?xml version="1.0" encoding="UTF-8"?>
<emotionml xmlns="http://www.w3.org/2009/10/emotionml" version="1.0"> 
    <vocabulary id="mycategories" type="category">
        <item name="love"/>
        <item name="hate"/>
    </vocabulary>
</emotionml>
```
And the exported emotion like this (the lengthy URI has been randomly generated when creating temporary file):
```
<?xml version="1.0" encoding="UTF-8"?>
<emotion xmlns="http://www.w3.org/2009/10/emotionml"
 category-set="file:/var/folders/z2/r6j9bq216vlc2jd4xm2chkd40000gn/T/myvocabularies7856475978942376452.xml#mycategories">
    <category name="love"/>
</emotion>
```

Note that this emotion is only valid locally. If one would distribute it as such, the category-set URI would not be resolved and would produce an exception.

## Example 8 - We can also embed the emotion and vocabulary in a document ##

Another possibility is to export not an emotion but a whole emotion document that contains both a vocabulary and an emotion that refers to this vocabulary locally. The only difference is that we add both the vocabulary and the emotion to the same document. To refer to the vocabulary inside the same document, the category-set URI only needs the fragment identifier (#`fragment`). When exporting the whole document, no exception is thrown. But note that if we would export the sole emotion, an exception would be thrown.

```
// create vocabulary
String myVocabularyId = "mycategories"; 
Vocabulary vocabulary = new Vocabulary(myVocabularyId, VocabularyType.CATEGORY);
vocabulary.addItem("love");
vocabulary.addItem("hate");

// add it to a new doc 
EmotionMLDocument doc = new EmotionMLDocument();
doc.add(vocabulary);

Emotion emotion = new Emotion(new Category("love"));
emotion.setCategorySetURI("#" + myVocabularyId); // the URI is local to the document 
doc.add(emotion);

new EmotionMLExporter().export(doc, System.out); // no exception since the vocabulary is contained in the same document
```

It produces the given document:
```
<?xml version="1.0" encoding="UTF-8"?>
<emotionml xmlns="http://www.w3.org/2009/10/emotionml" version="1.0">
    <vocabulary id="mycategories" type="category">
        <item name="love"/>
        <item name="hate"/>
    </vocabulary>
    <emotion category-set="#mycategories">
        <category name="love"/>
    </emotion>
</emotionml>
```

## Example 9 - An example of complex emotion ##

So far so good, we can now look at a more complex emotion. This one has one dimension called "valence" with a trace information and a high confidence. It is expressed through the face modality, more precisely the eyebrows and is attached to a text. The emotion is expressed in a mpeg video. It has independent relative timestamp:

```
Dimension dimension = new Dimension(FsreDimensions.VALENCE, new Trace(100, new float[] { 0.1f, 0.2f, 0.3f }));
dimension.setConfidence(0.8f);

Emotion emotion = new Emotion();
emotion.add(dimension); 
emotion.add(new ExpressedThrough(ExpressedThrough.Type.FACE));
emotion.add(new ExpressedThrough("eyebrows"));
emotion.setText(new EmotionText("this is sad but gets better")); 
emotion.add(new Reference(new URI("myvideo.mpeg"), "video/mpeg", Role.EXPRESSED_BY));

Timestamp timestamp = new Timestamp();
timestamp.setOffsetToStart(300);
timestamp.setTimeRefURI(new URI("#mysessionId"));
timestamp.setTimeRefAnchorPoint(TimeRefAnchorPoint.START);

emotion.setTimestamp(timestamp);
new EmotionMLExporter().export(emotion, System.out);
```

It produces:
```
<?xml version="1.0" encoding="UTF-8"?>
<emotion xmlns="http://www.w3.org/2009/10/emotionml"
         dimension-set="http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions"
         offset-to-start="300"
         time-ref-uri="#mysessionId"
         time-ref-anchor-point="start" 
         expressed-through="face eyebrows">
    <dimension confidence="0.8" name="valence">
        <trace freq="100.0Hz" samples="0.1 0.2 0.3"/>
    </dimension>
    <reference media-type="video/mpeg" role="expressedBy" uri="myvideo.mpeg"/>this is sad but gets better</emotion>
```

## Example 10 - Export to JSON ##

While not part of the EmotionML specification, the library also provides import/export from JSON. The library uses StAXON and its mapping convention (see [Mapping convention in StAXON](https://github.com/beckchr/staxon/wiki/Mapping-Convention)) to export JSON from XML and import XML from JSON. Instead of using EmotionMLExporter, one has to use JsonEmotionMLExporter as the following example shows:

```
Emotion emotion = new Emotion(new Dimension(FsreDimensions.VALENCE, 0.2f));

String json = new JsonEmotionMLExporter().export(emotion);

emotion = new JsonEmotionMLImporter().importEmotion(json);
```

It will produce:
```
{
	"emotion": {
		"@xmlns": "http://www.w3.org/2009/10/emotionml",
		"@dimension-set": "http://www.w3.org/TR/emotion-voc/xml#fsre-dimensions",
		"dimension": {
			"@name": "valence",
			"@value": "0.2"
		}
	}
}
```

## Example 11 - But no textual content ##

Note that exporting to JSON has one restriction. It cannot export emotions that contains textual information.

```
Emotion emotion = new Emotion(Big6.SADNESS);
emotion.setText(new EmotionText("Alas, I will throw an exception when exporting in JSON..."));
new JsonEmotionMLExporter().export(emotion, System.out); // exception thrown because of mixed content
```

It produces the given error message:
```
fr.loria.synalp.emotionml.exceptions.EmotionMLException: Unable to write the given Element in JSON: Mixed content is not 
supported: 'Alas, I will throw an exception when exporting in JSON...'
```

There are two ways to workaround that restriction. First, one can embed the emotion in another JSON parent element that holds both the text and the emotion which would then be siblings. Second, one can make use of the info element of EmotionML and include the text inside that info element.


# Importing emotions #

Importing emotions consists in producing a Java object from an emotionml document or standalone emotion. It is simpler than exporting emotions since the emotion is already built in EmotionML. One has to create an instance of EmotionMLImporter, and choose wether to import the given input as an emotionml document or as a standalone emotion.

For an emotionml document:
```
EmotionMLDocument doc = new EmotionMLImporter().importDocument(file);
```

For a standalone emotion:
```
Emotion emotion = new EmotionMLImporter().importEmotion(file);
```

As for the export, the input is tested against EmotionML constraints. All the situations that we mentioned while exporting could happen when importing as well. For instance, when trying to import an emotion with a category without category-set, an EmotionMLValidationException will be thrown.

The validation process being the same when importing or exporting, it is also possible to deactivate the vocabulary resolution when importing:

```
EmotionMLImporter importer = new EmotionMLImporter(new EmotionMLValidator().setResolveVocabularies(false));
Emotion emotion = importer.importEmotion(file);
```