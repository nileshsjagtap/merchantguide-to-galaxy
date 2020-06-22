# Merchant Guide To Galaxy

## Solution

* A solution based on a simple yet seamless combination of `Object Oriented Programming` and `Function Programming` paradigms since `Scala` has powerful support for both

**Domain Objects**

* `RomanNumeral` - Sum Type ADT to describe a Roman Numeral
* `RomanNumber` - Product Type ADT to describe a list of Roman Numerals
* `AlienNumber` - Product Type ADT to describe an Alien Number containing token and roman numeral
* `AlienNumbers` - Product Type ADT to describe a list of Alien Numbers
* `Metal` - Product Type ADT to describe a metal containing metal name and per unit value
* `Metals` - Product Type ADT to describe a list of Metals
* `Sentence` - Sum Type ADT to describe all sentences
* `Error` - Sum Type ADT to describe all error conditions
* `InterGalacticCalculator` - Product Type ADT to generate answers to the questions and convert them to text

**Other Classes**
* `LessThan` - a simple `Type Class` which enables ADTs to perform less than check operation
* `LessThanEqual` - a simple `Type Class` which enables ADTs to perform less than equals check operation
* `Syntax` - a trait where all implicit classes for type class syntax are present
* `SentenceParser` - assists in parsing sentences.
* `Sequenceable` - a trait which enables ADTs to perform sequence operation
* `AppImplicits` - an object where all implicits are placed under one roof

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
* All `Error` conditions are managed using simple `algebraic data types`
* `InterGalacticCalculator` accepts Alien Numbers and Metals objects and performs calculations around it.

## Parsing
* Simple regular expressions and string split operations are used.

## Tests
* `RomanNumeralSpec` contains tests to validate creation and behaviour of `RomanNumeral`.
* `RomanNumberSpec` contains tests to validate creation and behaviour of `RomanNumber`.
* `AlienNumberSpec` : contains tests to validate creation and behaviour of `AlienNumber` and `AlienNumbers`.
* `MetalSpec` : contains tests for validating creation and behaviour of `Metal` and `Metals`.
* `SentenceParserSpec` contains tests for parsing the input sentences .
* `InterGalacticCalculatorSpec` contains tests to validate the calculations performed on domain objects.
* `IntegrationSpec` contains tests to check integration of various modules.
* `LessThanSpec` contains tests to check validity of laws applicable to various instances of `LessThan` type class.
* `LessThanEqualSpec` contains tests to check validity of laws applicable to various instances of `LessThanEqual` type class.
* `SequenceableSpec` contains tests to validate the functionality of sequence operation

## Things which can be considered (improvements)

* Algorithm performance can be improved by reducing the number of iteration.

## Others Highlights
* One of the major reason for using `Scala 2.12` because it gave flatMap on Either one of the sweet things.
* All function doing recursion are mostly tailRec

## How to run the application
Once application running it'll behave as stated in the problem statement.

**$> sbt "run \<inputFilePath> \<outputFilePath>"**

**Note**: Need to have sbt installed on the machine
## How to run tests

**$> sbt test**

