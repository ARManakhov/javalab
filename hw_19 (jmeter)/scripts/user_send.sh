 #!/bin/bash


SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")

SCENARIO_PATH="$SCRIPTPATH/../scenarios/user_send.jmx"
LOG_FILE_NAME="$SCRIPTPATH/../result/user_send_$(date +'%m-%d-%Y-%HH-%MM-%SS').jtl"
RUN_LOG_FILE_NAME="$SCRIPTPATH/../log/user_send_$(date +'%m-%d-%Y-%HH-%MM-%SS').jtl"

JMETER_PATH="${JMETER_PATH:-$DEFAULT_JMETER_PATH}"
OMS_HOSTNAME="${OMS_HOSTNAME:-$DEFAULT_OMS_HOSTNAME}"
AUTH="${AUTH:-$DEFAULT_AUTH}"

echo "staring scenario '$SCENARIO_PATH', result path: '$LOG_FILE_NAME', log path: $RUN_LOG_FILE_NAME"

sh $JMETER_PATH -n -t $SCENARIO_PATH -Joms.hostname=$OMS_HOSTNAME -Jtarget.rate=$TARGET_RATE -Jtarget.holdTime=$HOLD_TIME -Jauth="$AUTH" -l $LOG_FILE_NAME -j $RUN_LOG_FILE_NAME

#==== Описание теста ====
# Циклично отпправляет TARGET_RATE запросов в минуту в течение HOLD_TIME минут на эндпоинты:
# POST /order/create (параметры запроса берутся из csv таблиц в папке resource)
# POST /message/${messageId}/send?orderId=${orderId} (отключен)
# GET /order/${orderId}
# GET /item/${orderId}/extendedlist (отключено)
# GET /shipping/${orderId}
# GET /order/${orderId}/marketing
# GET /order/${orderId}/history
# GET /promo/${promoId}/info (отключено)
# GET /warehouse/${warehouseId}/info (отключено)
# POST /delivery/pickupstores (оключено)
#
# так же в скрипте есть отключенные дубли вышеуказанных действий
#
#работеат на момент 22.11.2020
