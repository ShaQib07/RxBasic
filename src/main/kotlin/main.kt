import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observables.ConnectableObservable

fun main() {
    flatMapOperator()
}

fun simpleLamdaObserver() {
    val source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    source
        .subscribe(
            { println(it) },    // onNext as lamda
            { println(it.message) },    // onError as lamda
            { println("||  All Done  ||") }     // onComplete as lamda
        )
}

fun connectableObservable() {
    // ConnectableObservable is hot in nature
    val source = ConnectableObservable
        .just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
        .publish()

    // Observer 1
    source.subscribe { println(it) }

    // Observer 2
    source
        .map { it.length }
        .subscribe { println(it) }

    // Fire emission
    source.connect()
}

fun observableFromCallable() {
    val list = mutableListOf<Int>()

    // fromCallable passes any error down to it's observer
    Observable.fromCallable { list[1] }
        .subscribe(
            { println("Received $it")},
            { println("Error captured ${it.message}")}
        )
}

fun flatMapOperator() {
    val source: Observable<String> = Observable.just(
        "521934/2342/FOXTROT",
        "21962/12112/78886 /TANGO",
        "283242/4542/WHISKEY/2348562"
    )
    source
        .flatMap { Observable.fromArray(it.split("/")) }
        .subscribe { println(it.toString()) }
}



/*
|| ---------------------------------------- SOME IMPORTANT NOTES ---------------------------------------- ||

# If we use subscribeWith() instead of subscribe(),
RxJava is more likely to handle the disposing of the observer by itself.

# Suppressing Operators - suppress emissions that fail to meet a specified criterion - filer()
# Transforming Operators - transform emissions - map()
# Reducing Operators - take a series of emissions and consolidate them into a single emission
                       (usually emitted through a Single) - count()
# Collection Operators - accumulate all emissions into a collection such as a list or map and
                         then emit that entire collection as a single emission. Collection
                         operators are another form of reducing operators since they consolidate
                         emissions into a single one - toList()
# Error Recovery Operators - attempt re-subscribing/switch to an alternate source - onErrorReturn()
# Action Operators - can assist in debugging/getting visibility into an Observable chain - doOnNext()

# flatMap() - performs a dynamic Observable.merge() by taking each emission and mapping it to an Observable.
              Then, it merges the emissions from the resulting Observables into a single stream.
*/