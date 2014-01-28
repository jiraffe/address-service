var addressModule = (function () {

    // privates
    var service_url = "/addresswrap/",
        checkAddressUrl = "check?",
        addAddressUrl = "add",
        isFormValid = false;

    function request(type, url, opts, callback) {
        var xhr = new XMLHttpRequest(),
            fd,
            params;

        if (typeof opts === 'function') {
            callback = opts;
            opts = null;
        }

        xhr.open(type, url);

        if (type === 'POST' && opts) {

            xhr.setRequestHeader('Accept', 'application/json');
            xhr.setRequestHeader('Content-Type', 'application/json');

            params = JSON.stringify(opts);
        }

        xhr.onload = function () {
            callback(JSON.parse(xhr.response));
        };

        xhr.send(opts ? params : null);
    }

    function callback(response)   {
        console.log(response);
        var errorsBox = document.querySelector("[name='errorsBox'");
        if(!response.success)  {
            
            errorsBox.style.display = "block";
            errorsBox.innerHTML = response.message;
            return;
        }

        errorsBox.style.display = "none";
        errorsBox.innerHTML = " ";

        var coords = [response.data.latitude, response.data.longitude];

        if(mapModule.placemark) {
            mapModule.placemark.geometry.setCoordinates();
        }
        else {
            mapModule.placemark = new ymaps.Placemark(coords);
            mapModule.map.geoObjects.add(mapModule.placemark);
        }

        mapModule.map.setCenter(coords, 18);
        mapModule.getAddress(coords);
    }

    function checkAddress() {

        var city = document.querySelector("#cityInput").value,
            street = document.querySelector("#streetInput").value,
            building = document.querySelector("#buildingInput").value;
        

        request("GET", service_url + checkAddressUrl + "city=" + city + "&street=" + street + "&building=" + building, callback);
    }

    function addAddress () {
        var city = document.querySelector("#cityInput").value,
            street = document.querySelector("#streetInput").value,
            building = document.querySelector("#buildingInput").value,
            latitude = document.querySelector("#latitudeInput").value.substring(0, 10),
            longtitude = document.querySelector("#longtitudeInput").value.substring(0, 10),
            synonyms = document.querySelector('#synonymsInput').value,
            errorsBox = document.querySelector("[name='errorsBox'"),
            i;

            if(!city || !street || !building || !latitude || !longtitude)   {

                errorsBox.style.display = "block";
                errorsBox.innerHTML = "Необходимо заполнить все поля";
                return;
            }

            synonyms = synonyms.split(',');
            for(i = 0; i < synonyms.length; i++)    {
                synonyms[i] = synonyms[i].trim();
            }

            errorsBox.style.display = "none";

            request('POST', service_url + addAddressUrl, {
                city: city,
                street: street,
                building: building,
                latitude: latitude,
                longtitude: longtitude,
                synonyms: synonyms + ''
            }, callback);
    }

    return {
        check: checkAddress,
        add: addAddress,
        isFormValid: isFormValid
    };

})();