# gomo Video Code Challenge
Code challenge for gomo Video application

### Challenge Description
One way we try to make sense of this data is the concept of “unique view time”, or UVT: that is, a metric that can answer the question, “How much of a given video has this user watched at least once?”. For example, if I watch the first two minutes of the video and then go back and rewatch seconds 30 through 45, the UVT is still two minutes. Conversely, if I watch the first minute and the last minute of a two-hour video, my UVT is also two minutes.

Your objective is to write a program that accepts a collection of viewed fragments as input and outputs the UVT. Viewed Fragments will consist of the start and ending time in ms of a given watched fragment of video. (It is up to you how to represent viewed fragments in your code.) The program should not assume that input is sorted in any particular way.

### Expectations
- Written in language and IDE of developer's choice
- Well-structured code that is easily readable and understandable by other developers.
- Test cases for the UVT calculation algorithm
- Your program must be executable and include a README explaining how to run it (and build it, if applicable). Assume your audience is a competent developer who does not have prior experience with the specific frameworks/languages you used

## Instructions
This is a java application and will only work if Java is installed on your machine. If not, you can download the java runtime environment from the [Oracle website](https://www.java.com/en/download/).

To run this application, download the .zip folder from release v1.0 in the "Release" tab. Unzip the folder and make sure you keep the jar file in the same directory as the executables. Double-click on either the Simple or Verbose executable - Verbose has step-by-step logging and Simple will just output the total.

A command line will come up prompting you to enter the timestamps of start and end times of a viewed video. The start and end times should be entered as a space-delimited list, with the first number representing a start and the second number representing an end, the third reprersenting a start, etc. They do not need to be in time order but starts and ends must be adjacent with start before the end.

Example:
```
> Input view time segments: 2044981 5023664 8733444 10984666 1022333 1243890
```
