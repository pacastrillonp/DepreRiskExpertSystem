;hechos
(deffacts hechos
	(saludo)
        (recomendaciones))

(deftemplate usuario
	(slot nivelRiesgo (type NUMBER))
	(slot horasSueno (type NUMBER))
)

(defrule recomendaciones
	(usuario
		(nivelRiesgo ?niv)
		(horasSueno ?sue))
	?hecho <- (recomendaciones)
	=> 
	(printout t "Recomendaciones: " crlf)
	
	(if (< ?niv 10) then 
		(printout t "Obtuviste un nivel de riesgo bajo, eso esta bien, si lo sientes necesario consulta a un psicologo." crlf)
	else 
		(if (< ?niv 20)  then
			(printout t "Obtuviste un nivel de riesgo medio, pero no te sientas triste, hay personas dispuestas a ayudarte, deberias consultar con un psicologo para que te asesore con las dudas que tienes." crlf)
			(printout t "Puedes hacer varias actividades que te ayuden a sentirte mejor, como salir a caminar, practicar algun deporte, o incluso hacer trabajo comunitario." crlf)
		else
			(printout t "Obtuviste un nivel de riesgo alto, No te alarmes! Te recomendamos que solicites una cita con un psicologo en cuanto puedas para que sea el quien te ayude mejor." crlf)
			(printout t "Puedes hacer varias actividades que te ayuden a sentirte mejor, como salir a caminar, practicar algun deporte, o incluso hacer trabajo comunitario." crlf)
		)
	)

	(if (< ?sue 4) then
		(printout t "Al parecer estas durmiendo muy poco. Esto puede tener consecuencias en tu salud, como fatiga, cansancio y excesiva somnolencia diurna." crlf)
		(printout t "Entendemos que no siempre se puede lograr dormir lo suficiente debido al trabajo u otras cosas, pero te recomendamos que intentes dormir un poco mas y que consultes a un medico, puede que tengas un trastorno del sueño." crlf)
	else
		(if (< ?sue 7) then
			(printout t "Parece que no est�s durmiendo lo suficiente, recuerda que esto puede afectar tu salud y disminuir tu rendimiento en tus actividades diarias." crlf)
			(printout t "Intenta dormir al menos unas 7h30min para que des lo mejor de ti dia a dia ." crlf)
		else
			(if (< ?sue 9) then
				(printout t "Tu tiempo de sue�o es el adecuado, que bien!." crlf)
			else 
				(printout t "Parece que estos durmiendo mas de lo recomendado. Te recomendamos que consultes a un medico." crlf)
			)
		)
	)

	(retract ?hecho)
)



