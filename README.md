# How to read this project
## How is this project done?
  This project is using he following technologies
  1) Clean architectur Implemented with a a self implementation of Datasources, and repositories that are working in a imperative way (corutines) and in 
  a reactive way using Flows.
  (I've choosen Flows insteda RxJava2 Observables, Flowables, Singles, Maybes and Completables just a test purpose despite RxJava it's my core knowledge)
  2) DataBinding for the UI implementation
  3) ConstraintLayout, MotionLayout
  4) Ktor for netwkor -> I've choosen it instead of retrofit because it's 100% implemented in Kotlin and its quite lightweit and easy to implement.
    The reason that being 100% kotlin is a good point is that it can be used in Kotlin Multiplatform projects as well.
  5) SqlDelight for the database  -> the reason to choose it instead of Room is
      1) It's 100% Kotlin and it's Kotlin multiplatform compatible
      2) It uses a separted file with a .sq extension tha can be shared between projects and generate automatically the queries that means that in a kotlin multiplatform project all the queries for all the platfors will be the same.
      3) The way of working is quite similar to Room and it allows to swith from flows to liveData and also switching from all the RxJava stck to liveData
  6) Hilt: Is the new Dependency injection for android that works on top of Dagger. Combined with The new Android 4.2 (that I used in it's canary versio 10 to develop this project) it works amazingly shiwing the dependenci¡y injection inspector.

## Structure CoreBusiness
   There is a package called coreBusiness where I've added all the domain logic. It has a features bases structure (for each model if distrubuted the interactors data sources modesl and so).
   Each feeature has its own dependency injection module that will help to see what depencencies are provided for that package.
   
   There is another package featues on top og the root package that are basically the screens. in this case there is just one as there is just one feature for "posts" and its detai (but I put it all together)
   Here I've used basically the autogenerated master detail template tha comes in the gallery of android studio and I've set the logic on top of that.
   
   ## Consideration
   
   1) The Project uses Kotlin coroutines and Flows just as a test purpose. I wanted to learn a bit deeper how they work and I found out some different behaviours between flows and RxJava Observables that made me do some workarrounds when implementing the tipical flatmap to concatenate calls.
   2) I used motion layout to implement the expend/shrink animation for the photos in teh albums list.
   3) As I'm still learning Flows some concurrency problems may happen. I have experience with Schedulers switching in RxJava but coroutine context switching doesn0t work exactly the same.
   4) I've implemented a Worker that I scheduled each 15 minutes that fetches all the data and stores it in th database (emulating a case where we want to have fresh data for instance). It's called `FetchDataWorker`
   
   
