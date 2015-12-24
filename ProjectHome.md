This is a Java implementation of the EmotionML W3C proposed recommendation by the SYNALP team at the LORIA laboratory. This implementation, developed in the context of the Empathic Products ITEA2 project, is still alpha and would need some reviews. This implementation is independent from the W3C.

Few links:
  * [Examples](https://code.google.com/p/loria-synalp-emotionml/wiki/ImportingExportingEmotions)
  * [Javadoc API](https://loria-synalp-emotionml.googlecode.com/svn/trunk/doc/index.html)
  * [Latest version download](https://docs.google.com/uc?authuser=0&id=0B5BKYrx4W0VdNlVlRklYa1Yzd3M)
  * [EmotionML W3C Recommendation](http://www.w3.org/TR/emotionml/)



# Introduction #

The EmotionML proposed recommendation is a W3C ongoing standard about representing emotions with a markup language. The main advantage of the standard is that it acknowledges for the lack of consensus on how to represent an emotion. It thus proposes to standardize the most general aspects of an emotion but lets the user define its own vocabulary for emotions. An emotion is described in terms of **descriptors**: category, dimension, appraisal and action-tendency which are defined with a name and an optional intensity value (except dimension for which it is mandatory).

For instance, the following example shows a mixture of sadness, anger and fear:
```
<emotion category-set="http://www.w3.org/TR/emotion-voc/xml#big6">
    <category name="sadness" value="0.3"/>
    <category name="anger" value="0.8"/>
    <category name="fear" value="0.3"/>
</emotion>
```

When using a descriptor, it is required to define a **vocabulary** that lists all allowed names for the "name" attribute. In the example, the emotion defines a **category-set** URI that refers to a category vocabulary. If this category-set is not defined, the emotion is not valid with respect to the standard. EmotionML has a number of constraints on how to build a valid emotion. See [EmotionML Proposed Recommendation](http://www.w3.org/TR/emotionml/) for a full description of the standard.


# Overview #

## General approach ##

The general approach of the library is to be rather liberal on how to build and modify emotions with respect to the EmotionML constraints, and only check those constraints when importing and exporting. For instance all descriptors can be described with a value or with a trace (a dynamic representation of value), but not both at the same time. Because it would be too cumbersome to verify the validity of emotions with regards to all constraints, the library allows for the construction of invalid emotions, for instance a category with both a trace and a value. When trying to export an invalid emotion or to import an invalid emotion, an EmotionMLValidationException is thrown.

## Vocabularies handling ##

An important feature of EmotionML is how it handles vocabulary references using URIs. When representing an emotion with descriptors, the emotion must refer to a valid vocabulary listing all possible names for a descriptor. It means that when representing a descriptor with a custom name, the user has to also provide an accessible vocabulary defining that name. While the library allows for that, the library proposes to alleviate the vocabulary management by reusing the vocabularies defined on [Vocabularies for EmotionML](http://www.w3.org/TR/emotion-voc/) and its [EmotionML equivalent](http://www.w3.org/TR/emotion-voc/). The package [fr.loria.synalp.emotionml.vocabularies.predefined](https://loria-synalp-emotionml.googlecode.com/svn/trunk/doc/index.html?fr/loria/synalp/emotionml/vocabularies/predefined/package-summary.html) defines some existing vocabularies that ease the construction of valid emotions.


## Example - Basic emotion ##

For instance, the following example shows how to build an emotion using a predefined vocabulary of categories called Big6. When adding such predefined category, the advantage is that the category-set of the emotion is automatically setup. It would not be the case when building an emotion with a custom name.
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


For more examples on how to export an emotion, please see [Exporting emotions](https://code.google.com/p/loria-synalp-emotionml/wiki/ImportingExportingEmotions).