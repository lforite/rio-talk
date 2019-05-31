# Request tracing in microservices architecture - A Kleisli tale
[![Build Status](https://travis-ci.org/lforite/rio-talk.svg?branch=master)](https://travis-ci.org/lforite/rio-talk)

In modern architectures, the load and responsibilities are shared across many services. It can be hard to debug, at scale.
In this presentation, we introduce a well known technique, i.e. using `Kleisli` as the main type to bring context to computations,
in order to correlate log traces.

[Link to the slides](https://lforite.github.io/rio-talk/slides/index.html)

## Repository structure

### Slides
Contains the presentation, crafted with [`ScalaJS + reveal.js`](https://github.com/pheymann/scala-reveal-js). 

Compile and open the presentation (in your default browser): 
```shell
sbt ";compileSlides; openSlides"
```

### Http4s example
Contains the concrete examples on how `Kleisli` can power an `http4s` app

Run the server locally:
```shell
todo
```

Run the tests:
```shell
todo
```
### Play example
Contains the concrete examples on how `Kleisli` can power a `Play` app
Run the server locally:
```shell
todo
```
Run the tests:
```shell
todo
```