<!DOCTYPE html>
<html lang="en">
<head>
    <title>Профиль</title>
    <#include "head.ftlh">
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css"/>
<body>
<#include "navbar.ftlh">
<form action="new_movement" method="post">
    <div>
        <label for="from_city">Город отправки
        </label>
    </div>
    <div>
        <select id="from_city" name="cityFromId">
            <#list cites as city>
                <option value="${city.id}">${city.name}</option>
            </#list>
        </select>
    </div>
    <div>
        <label for="to_city">Город назначения
        </label>
    </div>
    <div>
        <select id="to_city" name="cityToId">
            <#list cites as city>
                <option value="${city.id}">${city.name}</option>
            </#list>
        </select>
    </div>
    <div>
        <label for="transport">Город назначения
        </label>
    </div>
    <div>
        <select id="transport" name="transportId">
            <#list transports as transport>
                <option value="${transport.id}">${transport.model}</option>
            </#list>
        </select>
    </div>
    <div>
        <label>Цена
            <input type="text" name="price">
        </label>
    </div>
    <div>
        <label>Время отправки
            <div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
                                <input type="text" class="form-control datetimepicker-input"
                                       data-target="#datetimepicker1" name="departureDate"/>
                                <div class="input-group-append" data-target="#datetimepicker1"
                                     data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        $(function () {
                            $('#datetimepicker1').datetimepicker();
                        });
                    </script>
                </div>
            </div>
        </label>
    </div>
    <div>
        <label>Время прибытия
            <div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
                                <input type="text" class="form-control datetimepicker-input"
                                       data-target="#datetimepicker2" name="arrivalDate"/>
                                <div class="input-group-append" data-target="#datetimepicker2"
                                     data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        $(function () {
                            $('#datetimepicker2').datetimepicker();
                        });
                    </script>
                </div>
            </div>
        </label>
    </div>
    <div>
        <button type="submit"> Сохранить</button>
    </div>
</form>
</body>
</html>