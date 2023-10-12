# Принципы ООП

## Абстракция 

Этот принцип служит для того, чтобы устранить зависимость классов верхнего уровня от классов нижнего уровня за счёт 
введения интерфейсов.

Абстрагирование означает выделение значимой информации и исключение из рассмотрения незначимой. В ООП рассматривают 
лишь абстракцию данных (нередко называя её просто «абстракцией»), подразумевая набор наиболее значимых характеристик 
объекта, доступных остальной программе.

Пользователь типа данных не имеет прямого доступа к его реализации, но может работать с данными через предоставленный 
набор операций. Преимущество абстракции данных в разделении операций над данными и внутреннего представления этих данных,
что позволяет изменять реализацию, не затрагивая пользователей типа данных.


## Инкапсуляция

Инкапсуляция — свойство системы, позволяющее объединить данные и методы, работающие с ними, в классе. Одни языки 
(например, C++, Java или Ruby) отождествляют инкапсуляцию с сокрытием, но другие (Smalltalk, Eiffel, OCaml) различают 
эти понятия.

Термин «инкапсуляция» может означать следующее в разных языках программирования:

- Механизм языка, ограничивающий доступ одних компонентов программы к другим;
- языковая конструкция, связывающая данные с методами для их обработки.

Слово «инкапсуляция» происходит от латинского *in capsula* — «размещение в оболочке». Таким образом, инкапсуляцию можно 
интуитивно понимать как изоляцию, закрытие чего-либо инородного с целью исключения влияния на окружающее, обеспечение
доступности главного, выделение основного содержания путём помещения всего мешающего, второстепенного в некую условную 
капсулу (чёрный ящик).

### Сокрытие

Принцип проектирования, заключающийся в разграничении доступа различных частей программы ко внутренним компонентам друг 
друга.

## Наследование

Наследование — свойство системы, позволяющее описать новый класс на основе уже существующего с частично или полностью 
заимствованной функциональностью. Класс, от которого производится наследование, называется базовым, родительским или 
суперклассом. Новый класс — потомком, наследником, дочерним или производным классом.

В объектно-ориентированном программировании, начиная с Simula 67, абстрактные типы данных называются классами.

- Суперкласс (англ. super class), родительский класс (англ. parent class), предок, родитель или надкласс — класс, 
производящий наследование в подклассах, т. е. класс, от которого наследуются другие классы. Суперклассом может быть 
подкласс, базовый класс, абстрактный класс и интерфейс.

- Подкласс (англ. subclass), производный класс (англ. derived class), дочерний класс (англ. child class), класс потомок, 
класс наследник или класс-реализатор — класс, наследуемый от суперкласса или интерфейса, т. е. класс определённый через 
наследование от другого класса или нескольких таких классов. Подклассом может быть суперкласс.

- Базовый класс (англ. base class) — это класс, находящийся на вершине иерархии наследования классов и в основании 
дерева подклассов, т. е. не являющийся подклассом и не имеющий наследований от других суперклассов или интерфейсов. 
Базовым классом может быть абстрактный класс и интерфейс. Любой не базовый класс является подклассом.

- Интерфейс (англ. interface) — это структура, определяющая чистый интерфейс класса, состоящий из абстрактных методов. 
Интерфейсы участвуют в иерархии наследований классов и интерфейсов.

- Суперинтерфейс (англ. super interface) или интерфейс-предок — это аналог суперкласса в иерархии наследований, т. е. 
это интерфейс производящий наследование в подклассах и подинтерфейсах.

- Интерфейс-потомок, интерфейс-наследник или производный интерфейс (англ. derived interface) — это аналог подкласса в
иерархии наследований интерфейсов, т. е. это интерфейс наследуемый от одного или нескольких суперинтерфейсов.

- Базовый интерфейс — это аналог базового класса в иерархии наследований интерфейсов, т. е. это интерфейс, находящийся 
на вершине иерархии наследования.

Иерархия наследования или иерархия классов — дерево, элементами которого являются классы и интерфейсы. 

## Полиморфизм подтипов (Полиморфизм)

Полиморфизм подтипов (в ООП называемый просто «полиморфизмом») — свойство системы, позволяющее использовать объекты с 
одинаковым интерфейсом без информации о типе и внутренней структуре объекта. Другой вид полиморфизма — параметрический 
— в ООП называют обобщённым программированием.

Основные разновидности полиморфизма:

##### Ad-hoc-полиморфизм

Ad-hoc-полиморфизм (в русской литературе чаще всего переводится как «специальный полиморфизм» или 
«специализированный полиморфизм», хотя оба варианта не всегда верны) поддерживается во многих языках посредством 
перегрузки функций и методов, а в слабо типизированных — также посредством приведения типов.

В динамически типизируемых языках ситуация может быть более сложной, так как выбор требуемой функции для вызова может
быть осуществлён только во время исполнения программы.

Перегрузка — синтаксический механизм, позволяющий по единому идентификатору вызывать разные функции. Приведение типов — 
семантический механизм, осуществляемый для преобразования фактического типа аргумента к ожидаемому функцией, при
отсутствии которого произошла бы ошибка типизации. То есть, при вызове функции с приведением типа происходит исполнение 
различного кода для различных типов (предваряющего вызов самой функции)

##### Параметрический полиморфизм

Параметрический полиморфизм позволяет определять функцию или тип данных обобщённо, так что значения обрабатываются 
идентично вне зависимости от их типа. Параметрически полиморфная функция использует аргументы на основе поведения, 
а не значения, апеллируя лишь к необходимым ей свойствам аргументов, что делает её применимой в любом контексте, 
где тип объекта удовлетворяет заданным требованиям поведения.

##### Статический полиморфизм

Статическим называется такой полиморфизм, который реализуется во время компиляции программного кода. Как например
перегрузка методов.

##### Динамический полиморфизм

Суть динамического полиморфизма заключается в определении типов и их поведения во время выполнения. Например, 
использование наследования.