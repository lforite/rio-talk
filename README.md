# Request tracing in microservices architecture - A Kleisli tale
[![Build Status](https://travis-ci.org/lforite/rio-talk.svg?branch=master)](https://travis-ci.org/lforite/rio-talk)

In modern architectures, the load and responsibilities are shared across many services. It can be hard to debug, at scale.
In this presentation, we introduce a well known technique, i.e. using `Kleisli` as the main type to bring context to computations,
in order to correlate log traces.

[Link to the slides](https://lforite.github.io/rio-talk/slides/index.html)

## Repository structure

### Slides
Contains the presentation, crafted with [`ScalaJS + reveal.js`](https://github.com/pheymann/scala-reveal-js). 

Compile and open the presentation (in your default browser)
```shell
sbt ";compileSlides; openSlides"
```

### Play example
Contains the concrete examples on how `Kleisli` can power a `Play` app
Run the server locally
```shell
sbt "; project play; run"
```
Execute some requests and check out the logs:
```shell
# create a new user (requires the http4s app running)
curl -X POST -v -d '{
      "first_name": "John",
      "last_name": "Doe",
      "email": "john.doe@test.com"
    }' "http://localhost:9000/users" -H "Content-Type: application/json"
    
# retrieve it
curl -X GET -v"http://localhost:9000/users/{id}"
```

Run the tests:
```shell
sbt "; project play; test"
```

### Http4s example
Contains the concrete examples on how `Kleisli` can power an `http4s` app

Run the server locally
```shell
sbt "; project htt4ps; run"
```

Execute some requests and check out the logs
```shell
# create a new user
curl -X POST -v -d '{
      "email": "john.doe@test.com",
      "content": "Hello John !"
    }' "http://localhost:9000/users"
    
# retrieve it
curl -X GET -v -H "http://localhost:9000/users/{id}"
```