# Project_Reactor_Demo
This repository is all about Learning & Practicing new exciting programming paradigm..called "Project Reactor"

# Project Reactor :
Its an implementation of Project Reactor Specification. To use it, we’ll use Java Reactive Library code. Spring WebFlux uses Project Reactor.

# When to use Reactive Programming?
When there is need to build application that supports very high load.

# PRACTICING - REACTOR CORE and REACTOR TEST
Reactive Programming is a new programming paradigm, which is asynchronous and non-blocking. It is event/message driven, functional style coding. 
It also has properties of back-pressure on data streams..i.e. It says hold on & send me only those amount of data which is being requested, 
rather whole chunk. It has capability to filter only those amount of data which it can process & thus traffic remains smooth. I'm here trying 
to demonstrate functionalities of Reactor Core & Test module.

![Imperative Style Coding Architechture](https://github.com/AadityaUoHyd/Project_Reactor_Demo/blob/master/reactiveDemo/1.JPG)

![Imperative Style Coding Architechture](https://github.com/AadityaUoHyd/Project_Reactor_Demo/blob/master/reactiveDemo/1.1.JPG)

# Reactive Streams Specifications: 
-	Publisher – An interface, probably a server, DB, etc. It has subscribe() method.
-	Subscriber – An interface which will subscribe to data emitter (source). It has onSubscribe(), onNext(), onError(), and onComplete() methods.
-	Subscription – Publisher will return the “subscription” object when it gets subscribed by Subscriber.
-	Processor – This interface extends Publisher and Subscriber interface. It can behave like both.

![Imperative Style Coding Architechture](https://github.com/AadityaUoHyd/Project_Reactor_Demo/blob/master/reactiveDemo/2.JPG)

# Flux and Mono: 
These are reactive types that implements Reactive streams specification. A part of Reactive core module. Flux represents 0 to N elements, 
whereas Mono represents 0 to 1 elements.

![Imperative Style Coding Architechture](https://github.com/AadityaUoHyd/Project_Reactor_Demo/blob/master/reactiveDemo/3.JPG)
