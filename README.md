# Merchant Guide To Galaxy

## Solution

**Domain Objects**

* `AlienLanguage` class holds map of numbers used for intergalactic transactions and corresponding roman numeral.
* `MetalDictionary` class holds list of metals each describing metal name and its price per unit.
* `RomanNumber` object holding all info related to roman numbers and calculate corresponding arabic values.
* `Sentence` trait which assists in describing sentences. 

**Other Classes**
* `TextReader` for reading from input file.
* `TextWriter` for writing to output file.
* `Calculator` for performing calculations on transactions.
* `Parser` for parsing and validating input sentences.
* `SentenceExtractor` objects for evaluating sentences and extracting information.  
* `Validator`for validating contents of sentences parsed.
* `InterGalacticCalc` class for accepting input statements, creating domain objects and finding answers to questions.

**Algorithm details**

* Processing is divided into 4 code blocks 
  * reading input data from file
  * creation of domain objects
  * calculations performed around domain objects  
  * writing output data to file
* Loose coupling is maintained amongst above blocks to keep code maintainable
* Input Data is divided into below sentences
  * `AlienNumberStatement` input sentence which describes alien number e.g. `glob is I`
  * `MetalStatement` input sentence which describes metal e.g. `glob prok Gold is 57800 Credits`
  * `AlienNumberQuestion` input sentence which describes question related to alien number e.g. `how much is pish tegj glob glob ?`
  * `MetalQuestion` input sentence which describes question related to metal e.g. `how many Credits is glob prok Gold ?`
  * `UnrecognisedQuestion` input sentence which is unrelated to domain e.g. `how much wood could a woodchuck chuck if a woodchuck ?`
* `SentenceViolations` and `RomanNumberViolation` are managed using simple `algebraic types`
* `SentenceExtractor` are used to validate and extract information from input Sentences. For each type of sentence has a specific extractor. Each extractor maintains link to next extractor in sequence, so as to chain them one after another.
* `RomanNumberValidations` are also chained one after another, which makes adding more validations simple by just creating a validation and prepending it in the chain.
* `Calculator` accepts Alien Language and Metal Dictionary objects and performs calculations around it.
* `TextWriter` is used for writing output data in text format to a file.
* In future, one should easily be able to create another writer to write output data in another format.

## Parsing
* Simple regular expressions and string split operations are used.

## Tests
* `AlienLanguageSpec` : contains tests to validate creation of domain objects .
* `MetalDictionarySpec` : contains tests for validating creation of metal objects.
* `ParserSpec` contains tests for parsing and validating the input sentences .
* `IntegrationSpec` contains tests to check integration of various modules.
* `CalculatorSpec` contains tests to validate the calculations performed on domain objects.
* `RomanNumberUtilsSpec` contains tests to validate and evaluate corresponding roman number.
* `TextReaderSpec` contains tests for reading lines from input file.
* `TextWriterSpec` contains tests for writing output lines to output file.

## Things which can be considered (improvements)

* Algorithm performance can be improved by reducing the number of iteration.

## Others Highlights
* One of the major reason for using `Scala 2.12` because it gave flatMap on Either one of the sweet things.
* package object contains below type alias 
    1. `Questions` for a list of Sentences
    2. `Answers` for a list of Sentences
    3. `Sentences` for a list of input String
* All function doing recursion are mostly tailRec

## How to run the application
Once application running it'll behave as stated in the problem statement.

**$> sbt "run \<inputFilePath> \<outputFilePath>"**

**Note**: Need to have sbt installed on the machine
## How to run tests

**$> sbt test**

