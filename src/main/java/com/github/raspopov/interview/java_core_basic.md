# Java Core

## Collections

* Какой из списков (**LinkedList**/**ArrayList**) лучше применять для добавления неопределенного количества элементов?

**LinkedList** лучше подходит в случае неопределенного количества элементов. Он занимает больше памяти в итоге.
**ArrayList** в процессе добавления может разрастись по потреблению памяти кратно больше чем **LinkedList**.

* Какой из списков (**LinkedList**/**ArrayList**) занимает больше памяти при одинаковом заполнении?

**LinkedList** занимает больше памяти из-за оберток значений и дополнительных ссылок между элементами. **ArrayList**
хранит значения в обычном массиве.

* Как происходит удаление элементов из **ArrayList**?

При удалении произвольного элемента из списка, все элементы находящиеся правее смещаются на одну ячейку влево и 
реальный размер массива (его емкость, capacity) не изменяется. Автоматического расширение массива существует, 
автоматическое сжатие нет, можно только явно выполнить сжатие командой **trimToSize**.

* Какая реализован **HashMap**, **HashSet**?

**HashMap** реализован как массив нод, где позиция в массиве = хэш-код ключа. При коллизии хэш-кодов, предыдущая нода
начинает хранить ссылку на следующую ноду. При этом проверка на то какое значение заменить, а какое вернуть происходит по
методу **equals** у ключа. При достижении порогового значения количества нод под одним хэш-кодом (в java 17 пороговое
значение 8), ноды превращаются из связанного списка в дерево. Так же происходит и обратный процесс из дерева в связный
список (в java 17 пороговое значение 6).

**HashSet** реализован через **HashMap**, где ключ это хранимое значение, а значение это константный объект.

* Чем отличаются **Iterable** и **Iterator**?

**Iterable** - интерфейс предоставляющий методы создания **Iterator** и **Spliterator**, метод **forEach** для 
применения метода потребителя к каждому элементу. Так же этот интерфейс позволяет использовать объект в улучшенном
цикле for-each.

**Iterator** - предназначен для итерации по элементам коллекции

* Чем отличаются коллекции и стримы (**Stream API**)?

Коллекции - набор структур данных. Stream API - метод работы с коллекциями.

* Какие есть виды операций в **Stream API**?

Промежуточные (intermediate) - обрабатывают поступающие элементы и возвращают **Stream**. Промежуточных операторов в 
цепочке обработки элементов может быть несколько.

Терминальные (terminal) - обрабатывают элементы и завершают работу стрима.

* Операция **forEach** промежуточная или терминальная?

Терминальная

* Какой есть аналог **forEach** в промежуточных операциях?

**peek**

* Для чего нужны **Optional**?

**Optional** может содержать, а может не содержать значение. Применяется для обозначения того что метод может вернуть
пустоту и что необходима проверка на наличие значения. Использование **Optional** позволяет избежать неожиданных **NPE**

* Чем отличаются **Oprional.orElse** и **Optional.orElseGet**?

**orElse** - возвращает хранимое в **Optional** значение, в случае если **Optional** пустой возвращается значение 
переданное в качестве аргумента.

**orElseGet** - возвращает хранимое в **Optional** значение, в случае если **Optional** пустой используется переданный 
**Supplier** для получения значение, которое будет возвращено

* Какая иерархия исключений? Для чего применяется каждый тип исключений?

**Throwable** - базовый интерфейс для всех исключений.

**Exception** - **Checked** - **Проверяемые** - основной тип исключений для программных ошибок. Требуют явного перехвата
в блоке _try-catch_ либо дальнейшего пробрасывания с помощью _throws_. Проверяемые исключения представляют собой те 
исключения на появление, которых мы должны отреагировать какими-то действиями, чтобы восстановить работоспособность 
приложения. Как например **IOException** в случае если не получается открыть файл или **SQLException** в случае, если 
при работе с БД произошла ошибка.

**RuntimeException** - **Unchecked** - **Непроверяемые** - исключения не требующие явной обработки или пробрасывания.
Непроверяемые исключения являются ошибками программирования, то есть это те ошибки перехват и обработка которых не
восстановит работоспособность приложения. Задача программиста написать код так, чтобы подобных ошибок не было.
Например, **ArithmeticException** при делении на ноль мы можем отловить это исключение, но гораздо правильнее будет не 
допустить его появления в принципе. Так же и с **NullPointerException**.

**Error** - **Unchecked** - **Непроверяемые** - подкласс Throwable и является непроверяемой ошибкой. Ни одно нормальное
приложение не должно их перехватывать т.к. появление такой ошибки сигнализирует о ненормальном состоянии. Пример ошибок

**OutOfMemoryError** - нехватка памяти для работы приложения, **NoClassDefFoundError** - ошибка в случае если не удается
найти определение загруженного класса в процессе выполнения методов или создания нового объекта.

* Как создать и запустить поток в Java?

Создание потока - **new Thread()**, сделать потомка **Thread** и переопределить метод **run**, передать в конструкторе 
**Runnable**.

Запуск потока методом **start**.

**ExecutorService** из пакета **Concurrency** (**Executors.newSingleThreadExecutor**, **Executors.newFixedThreadPool**, 
**Executors.newSingleThreadScheduledExecutor**)

**CompletableFuture.supplyAsync(() -> "Hello");**

**new Timer(name).schedule(TimerTask, delay)**

* Какими инструментами можно управлять потоками и общими ресурсами в Java?

**synchronized** — блок синхронизации для получения блокировки над чем-то

**wait** — поток ожидает внутри блока синхронизации

**notify/notifyAll** — уведомляет поток/потоки ожидающие на этой блокировке

**join** — текущий поток ожидает пока закончит выполнение тот поток на котором был вызван join

**java.util.concurrent.atomic** — классы предоставляющие атомарный доступ к объектам

**ReentrantLock** — обычная блокировка

**Condition** - условия блокировки

**ReadWriteLock** — позволяет блокироваться на чтение/запись.  Множественное чтение, единичная запись

**CyclicBarrier** — ожидает пока придет несколько потоков. Когда они придут он их всех пропустит. И так по кругу

**CountDownLatch** — ожидает пока придет несколько потоков, как только они придут он их всех пропустит.

* Чем отличаются **Future** и **CompletableFuture**?

**Future** представляет результат выполнения асинхронного вычисления. Предоставляет методы для проверки готовности 
результата, получения результата с блокировкой текущего потока в случае если результат не высчитан, отмена вычисления.

**CompletableFuture** реализует **Future** и этапы завершения (**CompletionStage**) с возможностью комбинирования
результатов и построения цепочек **CompletableFuture** для выполнения асинхронных задач.