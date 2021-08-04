package org.aadi.reactiveProgramming.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class MonoAndFluxServices {

    public Flux<String> programmingFlux(){
        return Flux.fromIterable(List.of("Java","Python","Javascript")).log();
    }

    //Lets test it using JUnit.
    public Flux<String> programmingFluxMap(){
        return Flux.fromIterable(List.of("Java","Python","Javascript"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> programmingFluxFilter(int num){
        return Flux.fromIterable(List.of("Java","Python","Javascript"))
                .filter(s -> s.length()>num);

    }

    //FlatMap don't preserve order during emitting items.
    public Flux<String> programmingFluxFlatMapAsync(){
        return Flux.fromIterable(List.of("Java","Python","Javascript"))
                .flatMap(s->Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)))
                .log();
    }

    //ConcatMap will preserve order during emitting items, where as FlatMap don't do that.
    public Flux<String> programmingFluxConcatMapAsync(){
        return Flux.fromIterable(List.of("Java","Python","Javascript"))
                .concatMap(s->Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)))
                .log();
    }

    public Mono<String> programmingMono(){
        return Mono.just("Java");
    }

    public Mono<List<String>> programmingMonoFlatMap(){
        return Mono.just("Java")
                .flatMap(s->Mono.just(List.of(s.split(""))))
                .log();
    }

    //To convert Mono<String> to Flux<String> using FlatMapMany
    public Flux<String> programmingMonoFlatMapMany(){
        return Mono.just("Javascript")
                .flatMapMany(s->Flux.just(s.split("")))
                .log();
    }

    //To transform will do independent filtering & can be reused. Also here I used defaultIfEmpty() to not get error for empty return cases
    public Flux<String> programmingFluxTransform(int num){
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s->s.length() > num);
        return Flux.fromIterable(List.of("Java","Python","Perl", "Swift", "Scala", "Kotlin"))
                .transform(filterData)
                .defaultIfEmpty("Default")
                .log();
    }

    public Flux<String> programmingFluxTransformSwitchIfEmpty(int num){
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s->s.length() > num);
        return Flux.fromIterable(List.of("Java","Python","Perl", "Swift", "Scala", "Kotlin"))
                .transform(filterData)
                .switchIfEmpty(Flux.just("Golang", "Javascript", "Smalltalk"))
                .transform(filterData)           //Its upto your requirement to decide you want to use or not to use filterData
                .log();
    }

    //concat a static method. Concat emits the emissions from two or more Observables without interleaving them. It preserves order.
    public Flux<String> databaseFluxConcat(){
        var sqlType = Flux.just("MySQL", "Postgres", "Oracle", "MS-SQL");
        var noSqlType = Flux.just("MongoDB", "DynamoDB", "Neo4j", "Redis");

        return Flux.concat(sqlType,noSqlType).log();
    }

    //concatWith an instance method, will do same job as concat()
    public Flux<String> databaseFluxConcatWith(){
        var sqlType = Flux.just("MySQL", "Postgres", "Oracle", "MS-SQL");
        var noSqlType = Flux.just("MongoDB", "DynamoDB", "Neo4j", "Redis");

        return sqlType.concatWith(noSqlType);
    }

    //Lets try concantWith() for Mono's, which will eventually return a flux.
    public Flux<String> databaseMonoConcatWith(){
        var sqlType = Flux.just("MySQL");
        var noSqlType = Flux.just("MongoDB");

        return sqlType.concatWith(noSqlType);
    }

    //merge a static method. merge emits the emissions from two or more Observables with interleaving them. Don't preserves order. use mergeSequential() to get in sequence.
    // Same case is with mergeWith() an instance method which emits interleaving data, which I'm not implementing right now.
    public Flux<String> databaseFluxMerge(){
        var sqlType = Flux.just("MySQL", "Postgres", "Oracle", "MS-SQL")
                .delayElements(Duration.ofMillis(50));
        var noSqlType = Flux.just("MongoDB", "DynamoDB", "Neo4j", "Redis")
                .delayElements(Duration.ofMillis(60));
        //Output will be interleaved in nature, kind of zig-zag from each flux.
        //If time is increased then it'll fail in test as it'll again time for sqlType to emit, thus have to calculate output accordingly.

        return Flux.merge(sqlType,noSqlType);
    }

    public Flux<String> databaseFluxMergeWithSequential(){
        var sqlType = Flux.just("MySQL", "Postgres", "Oracle", "MS-SQL")
                .delayElements(Duration.ofMillis(50));
        var noSqlType = Flux.just("MongoDB", "DynamoDB", "Neo4j", "Redis")
                .delayElements(Duration.ofMillis(75));
        // Here, output will be sequential & not interleaved.
        //If time is increased then it won't fail test-case, as its sequential in nature.

        return Flux.mergeSequential(sqlType,noSqlType);
    }

    public Flux<String> databaseFluxZip(){
        var sqlType = Flux.just("MySQL", "Postgres", "Oracle", "MS-SQL");
        var noSqlType = Flux.just("MongoDB", "DynamoDB", "Neo4j", "Redis");
        //Output will be mix of oneToOne. say, MySQLMongoDB

        return Flux.zip(sqlType, noSqlType,
                (first,second) -> (first+second));
    }

    //Zip can go up to max 8 publishers, here lets try with 3.
    public Flux<String> databaseFluxZipTuple(){
        var sqlType = Flux.just("MySQL", "Postgres", "Oracle", "MS-SQL");
        var noSqlType = Flux.just("MongoDB", "DynamoDB", "Neo4j", "Redis");
        var extraDB = Flux.just("Cassandra", "BigTable", "Scylla","SQLite");
        //Output will be mix of oneToOne. say, MySQLMongoDB

        return Flux.zip(sqlType, noSqlType, extraDB).map(objects -> objects.getT1()+objects.getT2()+objects.getT3());
    }

    //Lets try zip, one for mono. will use zipWith which is instance method and not static alike zip.
    public Mono<String> databaseMonoZipWith(){
        var sqlType = Mono.just("MySQL");
        var noSqlType = Mono.just("MongoDB");
        //Output will be mix of oneToOne. say, MySQLMongoDB

        return sqlType.zipWith(noSqlType, (first,second) -> (first+second));
        //return sqlType.zipWith(noSqlType).map(objects -> objects.getT1()+objects.getT2());     //both style will work.
    }

    //doOnNext() is used to do stuff before publisher sends something next time.
    public Flux<String> programmingFluxFilterDoOn(int num){
        return Flux.fromIterable(List.of("Java","Python","Javascript","Go","Angular"))
                .filter(s->s.length()>num)
                .doOnNext(s->{
                    System.out.println("s = "+s);
                })
                .doOnSubscribe(subscription -> {
                    System.out.println("Subscription = "+subscription.toString());
                })
                .doOnComplete(()->System.out.println("Completed!"));
    }

    //ExceptionHandling
    public Flux<String> programmingFluxOnErrorReturn(){
        return Flux.just("Java","Python")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred.")))
                .onErrorReturn("Javascript");
    }

    public Flux<String> programmingFluxOnErrorContinue(){
        return Flux.just("Java","Python","Kotlin")
                .map(s->{
                    if(s.equalsIgnoreCase("Python"))
                       throw new RuntimeException("Exception Occurred.");
                    return s.toUpperCase();
                })
                .onErrorContinue((e,f)->{
                    System.out.println("e = "+e);
                    System.out.println("f = "+f);
        });
    }

    //To map your error to other custom exception
    public Flux<String> programmingFluxOnErrorMap(){
        return Flux.just("Java","Python","Kotlin")
                .map( s -> {
                    if(s.equalsIgnoreCase("Python"))
                        throw new RuntimeException("Exception Occurred.");
                    return s.toUpperCase();
                })
                .onErrorMap(throwable->{
                    System.out.println("throwable = "+throwable);
                    return new IllegalStateException("From onError Map");
                });
    }

    //Alike try-catch of imperative style coding.
    public Flux<String> programmingFluxOnError(){
        return Flux.just("Java","Python","Kotlin")
                .map( s -> {
                    if(s.equalsIgnoreCase("Python"))
                        throw new RuntimeException("Exception Occurred.");
                    return s.toUpperCase();
                })
                .doOnError(throwable->{
                    System.out.println("throwable = "+throwable);
                });
    }

    public static void main(String[] args) {

        MonoAndFluxServices monoAndFluxServices = new MonoAndFluxServices();

        monoAndFluxServices.programmingFlux().subscribe( s-> System.out.println("Flux of s = " + s));
        monoAndFluxServices.programmingFluxMap().subscribe( s-> System.out.println("Flux of s = " + s));
        monoAndFluxServices.programmingMono().subscribe( s-> System.out.println("Mono of s = " + s));

    }

}
