package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.template.WeatherService
import java.time.LocalDateTime


val mainScenario = Scenario {

    state("start") {
        activators {
            regex("/start")
            intent("Hello")
        }
        action {
            reactions.run {
                say(
                    "Хэй, я могу рассказать тебе о погоде :) " +
                            "Какой город тебя интересует? "
                )
            }
        }
    }

    state("weather") {
        activators {
            intent("Weather")
        }
        action {
            activator.caila?.run {
                val weatherService = WeatherService()
                val city = caila?.slots?.get("city")
                val response = weatherService.getDataWeather(city.toString())
                if (response.weather != null) {
                    val temperature = response.main.temp.toInt()
                    val feelsLike = response.main.feelsLike.toInt()
                    val type = response.weather[0].main
                    val description = response.weather[0].description
                    reactions.say(
                        "В городе ${city?.capitalize()} температура воздуха $temperature °C, " +
                                "ощущается как $feelsLike °C."
                    )
                    reactions.run {
                        var text = ""
                        if (type == "Rain" || type == "Thunderstorm" || type == "Drizzle") text =
                            "Кстати, на улице ${description}, советую взять зонтик :)"
                        if (temperature >= 25) text = "Ну и жаришка :) "
                        if (temperature <= 10) text = "Прохладно, советую одеться потеплее! "
                        if (temperature <= -5) text = "Ну и холодина :("
                        say(
                            "${if (text.isNotEmpty()) "$text\n" else ""} Хочешь узнать более подробный прогноз?"
                        )
                    }
                } else {
                    reactions.sayRandom(
                        "Так пока не понимаю :( Просто напиши название города и все получится",
                        "Ты точно не придумал этот город. Попробуй еще раз, пожалуйста.",
                    )
                }
            }
        }

        state("yes") {
            activators {
                intent("Yes")
            }
            action {
                activator.caila?.run {
                    val city = caila?.slots?.get("city")
                    val weatherService = WeatherService()
                    val response = weatherService.getDataWeather(city.toString())
                    reactions.say(
                        "Лови детали прогноза:\n\n" +
                                "влажность воздуха ${response.main.humidity}%, " +
                                "атмосферное давление ${response.main.pressure} мм рт. ст., " +
                                "скорость ветра ${response.wind.speed} м/с."
                    )
                }
            }
        }

        state("no") {
            activators {
                intent("No")
            }
            action {
                reactions.sayRandom(
                    "До встречи, хорошего ${if (LocalDateTime.now().hour < 17) "дня" else "вечера"}!",
                    "Обращайся, если что :)"
                )
            }
        }
    }

    state("bye") {
        activators {
            intent("Bye")
        }
        action {
            reactions.say(
                "До свидания."
            )
        }
    }

    fallback {
        reactions.sayRandom(
            "Ошибка, но я не виноват :)",
            "Не знаю, что-то я устал :)",
            "Извини, что-то пошло не так, не сердись :)"
        )
    }
}
