var mapModule = (function(){
    
    var placemark,
            address = {},
            formData = {},
            map;

    function init() {
        mapModule.map = new ymaps.Map('map', {
            center:[53.67, 23.82], // Grodno
            zoom:18,
            behaviors: ['default', 'scrollZoom']
        });

        formData.city = document.querySelector("#cityInput");
        formData.street = document.querySelector("#streetInput");
        formData.buildingInput = document.querySelector("#buildingInput");
        formData.latitude = document.querySelector("#latitudeInput"),
        formData.longitude = document.querySelector("#longtitudeInput");

        mapModule.map.controls
            .add('zoomControl', { left: 5, top: 5 })
            .add('mapTools', { left: 35, top: 5 });

        // adding click-event for map
        mapModule.map.events.add('click', function (e) {
            var coords = e.get('coords');
            
            if(mapModule.placemark) {
                mapModule.placemark.geometry.setCoordinates(coords);
            }
            
            else {
                mapModule.placemark = createPlacemark(coords);
                mapModule.map.geoObjects.add(mapModule.placemark);

                mapModule.placemark.events.add('dragend', function () {
                    getAddress(mapModule.placemark.geometry.getCoordinates());
                });
            }
            getAddress(coords);
        });

        function createPlacemark(coords) {
            return new ymaps.Placemark(coords, {
                preset: 'twirl#violetStretchyIcon',
                draggable: true
            });
        }
    }

    function initFormData(address) {
        var addressArr = address.split(/, /g);
        formData.buildingInput.value = addressArr.pop();
        formData.street.value = addressArr.pop();
        formData.city.value = addressArr.pop();
    }

    function setLatLon(coords) {
        formData.latitude.value = coords[0];
        formData.longitude.value = coords[1];

        console.log(formData);
    }

    function getAddress(coords) {
        ymaps.geocode(coords).then(function (res) {
            var firstGeoObject = res.geoObjects.get(0);
            initFormData(firstGeoObject.properties.get('text'));
        });
        console.log(this);
        setLatLon(coords);
    }

    return{
            map: map,
            placemark: placemark,
            init: init,
            getAddress: getAddress
        }

})();