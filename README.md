# Currency Exchange

## Introduction

This is simple cod demonstrating the integration of hilt, use of rxkotlin with room and loading data from multiple sources, and writing simple unit test. 

## Architecture
Clean archetecture in combination with MVI is used to observe view state change from the lower layer. changes from editext is ont obvserved.

## Testing
Run `testStagingUnitTest` to execute unit tests and report is saved in `app\build\reports\tests\testStagingUnitTest`

## UI
 - Spinner is used for listing currencies
 - For exchangerate listing recyclerview with adaptor 'https://github.com/haramjan/rcadapter'
 
Note: Comments are not added 
