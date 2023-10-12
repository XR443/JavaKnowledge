Сегодня поговорим о том как Unit тесты помогают решать проблемы в архитектуре приложения.

Для начала определим что мы имеем:

* Класс ***ModelA***
```java
public record ModelA(ModelB modelB) {}
```

* Класс ***ModelB***
```java
public record ModelB() {}
```

* Сервис ***ModelService***
```java
public class ModelService {

    public void save(ModelA modelA) {
        if(modelA.modelB() == null)
            throw new MissingParameterException("ModelB param missing");

        System.out.println("Saving...\nSaved!");
    }
}
```

* Исключение ***MissingParameterException***
```java
public class MissingParameterException extends RuntimeException{
    public MissingParameterException(String message) {
        super(message);
    }
}
```

* И тесты на этот сервис ***ModelServiceTest***
```java
public class ModelServiceTest {

    ModelService modelService;

    @BeforeEach
    void setUp() {
        modelService = new ModelService();
    }

    @Test
    public void save_ModelBSet_ModelASaved() {
        // GIVEN
        ModelA modelA = new ModelA(new ModelB());

        // WHEN
        ModelA saved = modelService.save(modelA);

        // THEN
        assertEquals(modelA, saved);
    }

    @Test
    public void save_ModelBNull_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(null);

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelService.save(modelA));
    }
}
```

Я думаю многие узнали код на своем проекте и это вполне нормально. Многие проекты выглядят именно так и давайте сделаем
классическое добавление бизнес логики.

Мы будем добавлять новое поле и новое условие. Наша модель А обзаведется моделью С, а наш сервис обзаведется проверкой 
этого поля.

* Код ***ModelA*** будет выглядеть так
```java
public record ModelA(ModelB modelB, ModelC modelC) {}
```

* Код ***ModelService*** так
```java
public class ModelService {

    public ModelA save(ModelA modelA) {
        if (modelA.modelB() == null)
            throw new MissingParameterException("ModelB param missing");

        if (modelA.modelC() == null)
            throw new MissingParameterException("ModelC param missing");

        System.out.println("Saving...\nSaved!");

        return modelA;
    }
}
```

* А наш тест будет ***ModelServiceTest*** так
```java
public class ModelServiceTest {

    ModelService modelService;

    @BeforeEach
    void setUp() {
        modelService = new ModelService();
    }

    @Test
    public void save_ModelBSetModelCSet_ModelASaved() {
        // GIVEN
        ModelA modelA = new ModelA(new ModelB(), new ModelC());

        // WHEN
        ModelA saved = modelService.save(modelA);

        // THEN
        assertEquals(modelA, saved);
    }

    @Test
    public void save_ModelBNull_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(null, new ModelC());

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelService.save(modelA));
    }

    @Test
    public void save_ModelCNull_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(new ModelB(), null);

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelService.save(modelA));
    }
}
```

На первый взгляд выглядит все нормально. Добавилось поле, добавилась проверка, добавился и доработались тесты.

Все нормально! Чего ты докопался?

А вот до чего. Если мы добавим ещё полей и проверок, то метод **save** будет расти, как и сам тест в итоге это приведет 
к тому что в один момент когда у нас в **ModelA** будет 100+ полей у нас будет очень сложный и мудреный конструктор,
который надо будет постоянно дорабатывать. А чтобы его не дорабатывать мы его вынесем в метод.

``` java
private ModelA getModelA(){
        return new ModelA(
                new ModelB(), 
                new ModelC()
                // И так далее
                );
    }
```

Такой вариант будет работать, но это как освежитель воздуха в комнате, в которой воняет. Не решает проблему, а всего лишь
маскирует.

Ключевая проблема заключается в нарушении S**O**LID. Был нарушен **Open-Closed Principle** и отсюда и растут ноги
всей проблемы.

Теперь решим эту найденную проблему и перепишем тесты.

* Сервисный класс превратится вот в это
```java
@RequiredArgsConstructor
public class ModelService {

    private final List<Consumer<ModelA>> checks;

    public ModelA save(ModelA modelA) {
        checks.forEach(consumer -> consumer.accept(modelA));

        System.out.println("Saving...\nSaved!");

        return modelA;
    }
}
```

* Наши проверки будут представлены следующими классами
```java
public class ModelBMissing implements Consumer<ModelA> {

    @Override
    public void accept(ModelA modelA) {
        if (modelA.modelB() == null)
            throw new MissingParameterException("ModelB param missing");
    }
}
```
```java
public class ModelCMissing implements Consumer<ModelA> {

    @Override
    public void accept(ModelA modelA) {
        if (modelA.modelC() == null)
            throw new MissingParameterException("ModelC param missing");
    }
}
```

Один Unit тест превратится в несколько Unit тестов 

* Тест сервиса
```java
@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

    ModelService modelService;
    @Mock
    Consumer<ModelA> constraint;

    @BeforeEach
    void setUp() {
        modelService = new ModelService(List.of(constraint));
    }

    @Test
    public void save_ChecksPassed_ModelASaved() {
        // GIVEN
        ModelA modelA = new ModelA(new ModelB(), new ModelC());

        // WHEN
        ModelA saved = modelService.save(modelA);

        // THEN
        assertEquals(modelA, saved);
    }

    @Test
    public void save_CheckNotPassed_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(null, new ModelC());

        Mockito.doThrow(MissingParameterException.class).when(constraint).accept(modelA);

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelService.save(modelA));
    }
}
```

* А так же два теста проверок
```java
public class ModelBMissingTest {

    ModelBMissing modelBMissing;

    @BeforeEach
    void setUp() {
        modelBMissing = new ModelBMissing();
    }

    @Test
    public void accept_ModelBSet_NoExceptionThrow() {
        // GIVEN
        ModelA modelA = new ModelA(new ModelB(), null);

        // WHEN
        // THEN
        assertDoesNotThrow(() -> modelBMissing.accept(modelA));
    }

    @Test
    public void accept_ModelBNotSet_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(null, null);

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelBMissing.accept(modelA));
    }
}
```
```java
public class ModelCMissingTest {

    ModelCMissing modelCMissing;

    @BeforeEach
    void setUp() {
        modelCMissing = new ModelCMissing();
    }

    @Test
    public void accept_ModelCSet_NoExceptionThrow() {
        // GIVEN
        ModelA modelA = new ModelA(null, new ModelC());

        // WHEN
        // THEN
        assertDoesNotThrow(() -> modelCMissing.accept(modelA));
    }

    @Test
    public void accept_ModelCNotSet_ThrowsMissingParameterException() {
        // GIVEN
        ModelA modelA = new ModelA(null, null);

        // WHEN
        // THEN
        assertThrows(MissingParameterException.class, () -> modelCMissing.accept(modelA));
    }
}
```

Всё!

Теперь при возникновении ошибки в одном из компонентов мы можем написать ещё один тест покрывающий конкретно
этот тест-кейс и тестировать этот компонент не затрагивая остальные.

Так же мы можем не изменяя логику сервиса добавлять новые проверки и не изменяя его тестов гарантировать прохождение 
тестов сервиса.

Вывод: соблюдать SOLID важно

P.S. Потом поговорим про то как обрабатывать ошибки вместо выбрасывания исключений