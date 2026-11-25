package CineShelfModel {
    doc /* Модель системы CineShelf: участники, данные, части системы,
          варианты использования и требования из SRS. */

    private import ScalarValues::*;

    // ---------- Перечисления ----------
    enum def UserRole {
        enum Guest;
        enum User;
        enum Administrator;
    }

    enum def WatchStatus {
        enum Planned;
        enum Watched;
    }

    enum def Priority {
        enum High;
        enum Medium;
        enum Low;
    }

    // ---------- Данные ----------
    item def Account {
        attribute id : String;
        attribute name : String;
        attribute email : String;
        attribute role : UserRole;
    }

    item def Genre {
        attribute id : String;
        attribute name : String;
    }

    item def Movie {
        attribute id : String;
        attribute title : String;
        attribute year : Integer;
        attribute durationMin : Integer;
        attribute description : String;
        attribute averageRating : Real;
        attribute ratingsCount : Integer;
        attribute isArchived : Boolean;
    }

    item def WatchlistEntry {
        attribute addedAt : String;
        attribute priority : Priority;
        attribute note : String;
        attribute status : WatchStatus;
    }

    item def Rating {
        attribute value : Integer;
        attribute watchedAt : String;
        attribute review : String;
    }

    // ---------- Части системы ----------
    part def UserInterface;
    part def Database;

    part def CatalogService {
        doc /* Хранит фильмы и справочник жанров, выполняет поиск и фильтрацию. */
        part movies : Movie[*];
        part genres : Genre[*];
        attribute pageSize : Integer = 20;
    }

    part def WatchlistService {
        doc /* Ведёт личный план к просмотру и список просмотренных. */
        part entries : WatchlistEntry[*];
    }

    part def RatingService {
        doc /* Принимает оценки и пересчитывает среднюю оценку фильма. */
        part ratings : Rating[*];
        attribute minRating : Integer = 1;
        attribute maxRating : Integer = 10;
    }

    part def StatsService {
        doc /* Считает личную статистику и топ-10 фильмов каталога. */
    }

    part def CineShelfApp {
        part ui : UserInterface;
        part catalog : CatalogService;
        part watchlist : WatchlistService;
        part ratings : RatingService;
        part stats : StatsService;
        part storage : Database;

        connect ui to catalog;
        connect ui to watchlist;
        connect ui to ratings;
        connect watchlist to storage;
        connect catalog to storage;
        connect ratings to stats;
    }

    part cineShelf : CineShelfApp;

    // ---------- Варианты использования ----------
    use case def BrowseCatalog {
        subject app : CineShelfApp;
        actor guest : Account;
    }

    use case def AddToWatchlist {
        subject app : CineShelfApp;
        actor user : Account;
        doc /* Фильм добавляется в план одним действием, дубликаты запрещены. */
    }

    use case def MarkWatched {
        subject app : CineShelfApp;
        actor user : Account;
        doc /* Фильм переходит из плана в просмотренные автоматически. */
    }

    use case def RateMovie {
        subject app : CineShelfApp;
        actor user : Account;
        doc /* Оценка от 1 до 10; повторная оценка заменяет предыдущую. */
    }

    use case def ViewStats {
        subject app : CineShelfApp;
        actor user : Account;
    }

    use case def ManageCatalog {
        subject app : CineShelfApp;
        actor administrator : Account;
    }

    // ---------- Требования ----------
    requirement def CineShelfRequirement {
        doc /* Базовый тип требований CineShelf. */
    }

    requirement def RatingScale :> CineShelfRequirement {
        attribute minValue : Integer = 1;
        attribute maxValue : Integer = 10;
    }

    requirement def ResponseTime :> CineShelfRequirement {
        attribute maxSeconds : Real;
        attribute concurrentRequests : Integer;
    }

    requirement <'REQ-FUNC-004'> viewCatalog : CineShelfRequirement {
        doc /* Каталог доступен всем ролям, включая гостя, постранично. */
    }

    requirement <'REQ-FUNC-010'> addToWatchlist : CineShelfRequirement {
        doc /* Добавление фильма из каталога в план одним действием. */
    }

    requirement <'REQ-FUNC-011'> noDuplicateEntry : CineShelfRequirement {
        doc /* Фильм не добавляется в план повторно. */
    }

    requirement <'REQ-FUNC-014'> markWatched : CineShelfRequirement {
        doc /* Отметка просмотра удаляет фильм из плана. */
    }

    requirement <'REQ-FUNC-015'> rateMovie : RatingScale {
        doc /* Личная оценка фильма от 1 до 10. */
    }

    requirement <'REQ-FUNC-016'> averageRating : CineShelfRequirement {
        doc /* Средняя оценка вычисляется автоматически. */
    }

    requirement <'REQ-PERF-002'> actionSpeed : ResponseTime {
        doc /* Добавление в план и оценка завершаются не более чем за 1 с при 50 одновременных запросах. */
    }

    requirement <'REQ-REL-001'> ratingIntegrity : CineShelfRequirement {
        doc /* Одна оценка на пользователя и фильм; средняя пересчитывается корректно. */
    }

    // ---------- Удовлетворение требований ----------
    satisfy viewCatalog by cineShelf.catalog;
    satisfy addToWatchlist by cineShelf.watchlist;
    satisfy noDuplicateEntry by cineShelf.watchlist;
    satisfy markWatched by cineShelf.watchlist;
    satisfy rateMovie by cineShelf.ratings;
    satisfy averageRating by cineShelf.ratings;
    satisfy actionSpeed by cineShelf.watchlist;
    satisfy ratingIntegrity by cineShelf.ratings;
}
