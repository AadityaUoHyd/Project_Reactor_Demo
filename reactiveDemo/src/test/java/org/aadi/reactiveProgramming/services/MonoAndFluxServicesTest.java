package org.aadi.reactiveProgramming.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static reactor.test.StepVerifierExtensionsKt.expectError;

class MonoAndFluxServicesTest {

    MonoAndFluxServices monoAndFluxServices;

    {
        monoAndFluxServices = new MonoAndFluxServices();
    }

    @Test
    @DisplayName("Happy path test")
    void testProgrammingFlux() {
        var programmingFlux = monoAndFluxServices.programmingFlux();

        StepVerifier.create(programmingFlux)
                .expectNext("Java","Python","Javascript")
                .verifyComplete();
    }

    /*@Test
    @DisplayName("Failure test for negative case")
    void testProgrammingFluxFailScenario() {
        var programmingFlux = monoAndFluxServices.programmingFlux();
        Assertions.assertNotNull(prices);
        /*StepVerifier.create(programmingFlux)
                .expectNext("Java","Golang","Javascript")
                ..expectError(AssertionError)
                .verifyComplete();
    }*/

    @Test
    void testProgrammingMono() {
        var programmingMono = monoAndFluxServices.programmingMono();

        StepVerifier.create(programmingMono)
                .expectNext("Java")
                .verifyComplete();
    }

    @Test
    void testProgrammingFluxMap() {
        var programmingFluxMap = monoAndFluxServices.programmingFluxMap();

        StepVerifier.create(programmingFluxMap)
                .expectNext("JAVA","PYTHON","JAVASCRIPT")
                .verifyComplete();
    }

    @Test
    void testProgrammingFluxFilter() {
        var programmingFluxFilter = monoAndFluxServices.programmingFluxFilter(5);

        StepVerifier.create(programmingFluxFilter)
                .expectNext("Python","Javascript")
                .verifyComplete();
    }

    @Test
    void testProgrammingFluxFlatMapAsync() {
        var programmingFluxFlatMapAsync = monoAndFluxServices.programmingFluxFlatMapAsync();

        StepVerifier.create(programmingFluxFlatMapAsync)
                .expectNextCount(20)
                .verifyComplete();

    }

    @Test
    void testProgrammingConcatFlatMapAsync() {
        var programmingFluxConcatMapAsync = monoAndFluxServices.programmingFluxConcatMapAsync();

        StepVerifier.create(programmingFluxConcatMapAsync)
                .expectNextCount(20)
                .verifyComplete();

    }

    @Test
    void testProgrammingMonoFlatMap() {
        var programmingMonoFlatMap = monoAndFluxServices.programmingMonoFlatMap();

        StepVerifier.create(programmingMonoFlatMap)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testProgrammingMonoFlatMapMany() {
        var programmingMonoFlatMapMany = monoAndFluxServices.programmingMonoFlatMapMany();

        StepVerifier.create(programmingMonoFlatMapMany)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    @DisplayName("Happy path")
    void testProgrammingFluxTransform() {
        var programmingFluxTransform = monoAndFluxServices.programmingFluxTransform(5);

        StepVerifier.create(programmingFluxTransform)
                .expectNext("Python","Javascript")
                .verifyComplete();
    }

    @Test
    @DisplayName("Negative flow path, when passed number is more than any length present, " +
                 "so empty return means it'll throw error, to avoid such case use defaultIfEmpty(\"Default\") method")
    void testProgrammingFluxTransformDefault() {
        var programmingFluxTransformDefault = monoAndFluxServices.programmingFluxTransform(8);

        StepVerifier.create(programmingFluxTransformDefault)
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    @DisplayName("Happy path test - when passed number is more than any length present in first Flux of fromIterable() list, " +
            "so empty return means it'll now switch to switchIfEmpty(), if again it'll find no output there then throw error, else u again use defaultIfEmpty()")
    void testProgrammingFluxTransformSwitchIfEmpty() {
        var programmingFluxTransformSwitchIfEmpty = monoAndFluxServices.programmingFluxTransformSwitchIfEmpty(8);

        StepVerifier.create(programmingFluxTransformSwitchIfEmpty)
                .expectNext("Javascript", "Smalltalk")
                .verifyComplete();
    }

    @Test
    void testDatabaseFluxConcat() {
        var databaseFluxConcat = monoAndFluxServices.databaseFluxConcat().log();

        StepVerifier.create(databaseFluxConcat)
                .expectNext("MySQL", "Postgres", "Oracle", "MS-SQL", "MongoDB", "DynamoDB", "Neo4j", "Redis")
                .verifyComplete();
    }

    @Test
    void testDatabaseFluxConcatWith() {
        var databaseFluxConcatWith = monoAndFluxServices.databaseFluxConcatWith().log();

        StepVerifier.create(databaseFluxConcatWith)
                .expectNext("MySQL", "Postgres", "Oracle", "MS-SQL", "MongoDB", "DynamoDB", "Neo4j", "Redis")
                .verifyComplete();
    }

    @Test
    void testDatabaseMonoConcatWith() {
        var databaseMonoConcatWith = monoAndFluxServices.databaseMonoConcatWith().log();

        StepVerifier.create(databaseMonoConcatWith)
                .expectNext("MySQL","MongoDB")
                .verifyComplete();
    }

    @Test
    void testDatabaseFluxMerge() {
        var databaseFluxMerge = monoAndFluxServices.databaseFluxMerge().log();

        StepVerifier.create(databaseFluxMerge)
                .expectNext("MySQL", "MongoDB", "Postgres", "DynamoDB", "Oracle", "Neo4j", "MS-SQL", "Redis")   //Output will be zig-zag..i.e. alternate (interleave).
                //If we make time gap say 25 millis b/w both, then MS-SQL will come after Oracle instead of Neo4j.
                .verifyComplete();
    }

    @Test
    void testDatabaseFluxMergeWithSequential() {
        var databaseFluxMergeWithSequential = monoAndFluxServices.databaseFluxMergeWithSequential().log();

        StepVerifier.create(databaseFluxMergeWithSequential)
                .expectNext("MySQL", "Postgres", "Oracle", "MS-SQL", "MongoDB", "DynamoDB", "Neo4j", "Redis")   //Output will be sequential.
                //If we make time gap say 25 millis b/w both, then no issue as its sequential in nature.
                .verifyComplete();
    }

    @Test
    void testDatabaseFluxZip() {
        var databaseFluxZip = monoAndFluxServices.databaseFluxZip().log();

        StepVerifier.create(databaseFluxZip)
                .expectNext("MySQLMongoDB", "PostgresDynamoDB", "OracleNeo4j", "MS-SQLRedis")
                .verifyComplete();
    }

    @Test
    void testDatabaseFluxZipTuple() {
        var databaseFluxZipTuple = monoAndFluxServices.databaseFluxZipTuple().log();

        StepVerifier.create(databaseFluxZipTuple)
                .expectNext("MySQLMongoDBCassandra", "PostgresDynamoDBBigTable", "OracleNeo4jScylla", "MS-SQLRedisSQLite")
                .verifyComplete();
    }

    @Test
    void testDatabaseMonoZipWith() {
        var databaseMonoZipWith = monoAndFluxServices.databaseMonoZipWith().log();

        StepVerifier.create(databaseMonoZipWith)
                .expectNext("MySQLMongoDB")
                .verifyComplete();
    }

    @Test
    void testProgrammingFluxFilterDoOn() {
        var programmingFluxFilterDoOn = monoAndFluxServices.programmingFluxFilterDoOn(5).log();

        StepVerifier.create(programmingFluxFilterDoOn)
                .expectNext("Python","Javascript","Angular")
                .verifyComplete();
    }

    @Test
    void testProgrammingFluxOnErrorReturn() {
        var programmingFluxOnErrorReturn = monoAndFluxServices.programmingFluxOnErrorReturn().log();

        StepVerifier.create(programmingFluxOnErrorReturn)
                .expectNext("Java","Python","Javascript")
                .verifyComplete();
    }

    @Test
    void testProgrammingFluxOnErrorContinue() {
        var programmingFluxOnErrorContinue = monoAndFluxServices.programmingFluxOnErrorContinue().log();

        StepVerifier.create(programmingFluxOnErrorContinue)
                .expectNext("JAVA","KOTLIN")
                .verifyComplete();
    }

    @Test
    void testProgrammingFluxOnErrorMap() {
        var programmingFluxOnErrorMap = monoAndFluxServices.programmingFluxOnErrorMap().log();

        StepVerifier.create(programmingFluxOnErrorMap)
                .expectNext("JAVA")
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void testProgrammingFluxOnError() {
        var programmingFluxOnError = monoAndFluxServices.programmingFluxOnError().log();

        StepVerifier.create(programmingFluxOnError)
                .expectNext("JAVA")
                .expectError(RuntimeException.class)
                .verify();
    }

}