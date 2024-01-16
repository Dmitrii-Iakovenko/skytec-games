# Тестовое задание для компании Skytec Games


## Контакты
telegram: @Skytec_Games (Юлия)


## Вакансия
[link](https://hh.ru/vacancy/91259590?hhtmFrom=chat)

**Предлагаем вам поучаствовать в разработке и поддержке сложных комплексных мультиплеерных игр, 
имеющих миллионы установок, большую пользовательскую базу и высокие оценки от игроков. 
Наши проекты быстро растут и развиваются, в связи с чем мы активно расширяем нашу команду 
перспективными сотрудниками.**

**Чем нужно будет заниматься?**
* Поддержка и доработка серверной части игр Android/iOS игр (в жанрах CCG, Online RPG Clicker и Action)
* Выпуск обновлений, сопровождение боевых игровых серверов
* Участие в разработке новых проектов компании
* Работа в удаленном формате

**Необходимые навыки:**
* Хорошее знание Java (SE, EE)
* Хорошее знание MySQL (сложные запросы, индексы, оптимизация)
* Опыт работы с memcached, nginx, apache, git, *nix;
* Умение быстро учиться и осваивать новое

**Будет плюсом**
* Опыт работы с Highload
* Опыт работы над игровыми проектами
* Опыт решения DevOps-задач

**Работа в Skytec Games — это:**
* Сложные и интересные задачи
* Возможность учиться у профессиональных коллег
* Белая зарплата и официальное трудоустройство (обсуждается по результатам собеседования)
* Гибкий график работы
* Оплачиваемый отпуск и больничный
* Компенсация посещения спортзалов
* Крутые корпоративы и тематические вечеринки

**Мы ждем от Вас:**
* Резюме
* Готовность выполнить тестовое задание
* 
**Напишите нам прямо сейчас и кто знает, может быть мы вместе с вами создадим новый шедевр в игровой индустрии?**


## Тех. задание:
[link](https://testtask.skytecgames.com/)

**Преамбула**

В нашей игре есть такая механика как кланы, иначе говоря скопления игроков объединенные общей целью.
У каждого клана есть своя казна с золотом. Есть различные способы пополнения казны клана. Можно выполнять задания,
сражаться на арене, просто пополнить казну из своего кармана и т.д. И конечно же мы следим за всеми действиями,
чтобы в случае чего служба поддержки могла как-то отвечать на вопросы пользователей в случае какого-то недопонимания.

**Примерная иерархия**

```java
// Упрощенный объект клана
public class Clan {
    private long id;     // id клана
    private String name; // имя клана
    private int gold;    // текущее количество золота в казне клана
}
```

```java
// Есть сервис, посвященный кланам. 
// Да это выглядит как 'репозиторий'. 
// Но это сервис, просто все остальные методы не нужны для примера
public interface ClanService {
    Clan get(long clanId);
}
```

```java
// Так же у нас есть ряд сервисов похожих на эти. 
// Их суть в том, что они добавляют(или уменьшают) золото в казне клана
public class UserAddGoldService { // пользователь добавляет золото из собственного кармана

    private final ClanService clans;

    public void addGoldToClan(long userId, long clanId, int gold) {
        Clan clan = clans.getClan(clanId);
        // clan.[gold] += gold;
        // как-то сохранить изменения
    }
}
```

```java
// Еще один такой сервис
public class TaskService { // какой-то сервис с заданиями

    private final ClanService clans;

    void completeTask(long clanId, long taskId) {
        // ...

        // if (success)
        {
            Clan clan = clans.getClan(clanId);
            // clan.[gold] += gold;
            // как-то сохранить изменения
        }
    }
}
```

Данная структура лишь пример, и вы можете менять её на свое усмотрение.


**Задачи**
1. Реализовать логику добавления/уменьшения золота в клан, при этом предусмотреть, что золото может
   зачислиться из сотни(100) разных потоков в один момент. Разными пользователями по разной причине.
2. Реализовать отслеживание разных действий начисления золота, чтобы служба поддержки смогла идентифицировать
   когда и по какой причине в казне изменилось количества золота, сколько было, сколько стало и т.д.


**Технические требования**

Задание нацелено на демонстрацию ваших знаний java, в частности знание многопоточности. В конце у вас должно
получиться функционирующее приложение, иначе говоря работающий код.

Также не стоит использовать высокоуровневые библиотеки такие как Spring, Hibernate. При необходимости лучше
использовать что-то на уровне JDBC.

**FAQ**

`Откуда в ClanService появляются новые кланы? Из бд? Или еще откуда-то?`  
На ваше усмотрение.

`Нужно реализовать полноценный REST сервис?`  
На ваше усмотрение.

`Нужно ли сделать например WEB интерфейс?`  
Вовсе не обязательно, это задание для backend разработчика. Конечно если вы его сделаете - будет круто 😎

`А что должно быть в REST/WEB интерфейсах?`  
Вы делаете или не делаете эти или какие-то другие интерфейсы на свое усмотрение, это не то что нас интересует.
Но вы можете сделать что угодно, если вам кажется что это добавит "дополнительных очков🏅".

`Нужно ли использовать базу данных? И какую?`  
На ваше усмотрение. Это было бы хорошей демонстрацией ваших знаний SQL.
Можете использовать любую удобную реляционную базу данных, например H2 - её не нужно отдельно устанавливать.

`Пффф... Это вы называете заданием? Просто сделаю блокировку на уровне бд. 😏`  
Задание не должно быть сконцентрировано вокруг бд, это не то что мы хотим проверить.
Мы ожидаем что вы проявите незаурядные знания работы с многопоточностью в java 😉

`Какую версию java использовать?`  
Версий 8/11 будет вполне достаточно.

`git или final_version_3(1).zip?`  
Лучше использовать гит.

`Подразумевается, что данное приложение будет работать в одном экземпляре
или в распределенной режиме (несколько экземпляров)?`  
В одном экземпляре.

`Нужно реализовать %ЭТУ% фичу %ТАК% или %ЭТАК%?`  
В случае если это не относится к техническим требованием, делайте так,
как вам кажется будет лучше на проекте с относительно большой нагрузкой.
Самостоятельность и инициативность!

`Куда задавать вопрос, если на него нет ответа здесь?`  
Написать нам на почту. Хотя скорее всего "На ваше усмотрение." 😁

`А что нужно сделать?`  
Нужно сделать Задачи