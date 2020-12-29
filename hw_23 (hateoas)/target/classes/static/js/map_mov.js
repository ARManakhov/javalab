var points;
function setpoints(p) {
    points=p;
    ymaps.ready(init);
}

function init() {
    /**
     * Создаем мультимаршрут.
     * Первым аргументом передаем модель либо объект описания модели.
     * Вторым аргументом передаем опции отображения мультимаршрута.
     * @see https://api.yandex.ru/maps/doc/jsapi/2.1/ref/reference/multiRouter.MultiRoute.xml
     * @see https://api.yandex.ru/maps/doc/jsapi/2.1/ref/reference/multiRouter.MultiRouteModel.xml
     */
    let centerPoint = [(points[0][0] + points[1][0]) / 2, (points[0][1] + points[1][1]) / 2];
    var myMap = new ymaps.Map("map", {
            center: centerPoint,
            zoom: 5
        }, {
            searchControlProvider: 'yandex#search'
        });

    myMap.geoObjects
        .add(new ymaps.Placemark(points[0], {
            balloonContent: '<strong>Город отправки</strong>'
        }, {
            preset: 'islands#icon',
            iconColor: '#0095b6'
        }))
        .add(new ymaps.Placemark(points[1], {
            balloonContent: '<strong>Город прибытия</strong> цвет'
        }, {
            preset: 'islands#dotIcon',
            iconColor: '#735184'
        }));
}
