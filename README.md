# gomo Video Code Challenge
Code challenge for gomo Video application

### Challenge Description
One way we try to make sense of this data is the concept of “unique view time”, or UVT: that is, a metric that can answer the question, “How much of a given video has this user watched at least once?”. For example, if I watch the first two minutes of the video and then go back and rewatch seconds 30 through 45, the UVT is still two minutes. Conversely, if I watch the first minute and the last minute of a two-hour video, my UVT is also two minutes.

Your objective is to write a program that accepts a collection of viewed fragments as input and outputs the UVT. Viewed Fragments will consist of the start and ending time in ms of a given watched fragment of video. (It is up to you how to represent viewed fragments in your code.) The program should not assume that input is sorted in any particular way.

### Expectations
- Written in language and IDE of developer's choice
- Well-structured code that is easily readable and understandable by other developers.\
- Test cases for the UVT calculation algorithm
- Your program must be executable and include a README explaining how to run it (and build it, if applicable). Assume your audience is a competent developer who does not have prior experience with the specific frameworks/languages you used
