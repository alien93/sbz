(import projara.model.items.Item)
(import projara.model.items.ItemCategory)
(import projara.model.users.*)
(import projara.model.shop.*)
(batch projara/resources/jess/model_templates.clp)

(defrule umanji_za_poene
    "Umanjuje ukupni racun za poene"
    (declare (salience 2)(no-loop TRUE))
    ?bill <- (Bill (spentPoints ?spentP &:(> ?spentP 0))(total ?total)(discountPercentage ?disc)(originalTotal ?origTot))
    =>
    (modify ?bill (total (- ?total (/ (* ?origTot ?spentP) 100)) ))
    (modify ?bill (discountPercentage (+ ?disc ?spentP)))
    )

(defrule dodaj_poene
    "Dodaje poene na osnovu konacne cene racuna"
    (declare (salience 1)(no-loop TRUE))
    ?custCat <- (CustomerCategory (OBJECT ?custCatOBJ)(categoryCode ?custCatCode))
    ?customer <- (Customer (OBJECT ?custObj)(category ?cat &:(call ?cat equals ?custCatOBJ)))
    ?bill <- (Bill (OBJECT ?billOBJ)(customer ?cust &:(call ?cust equals ?custObj))(total ?total)(awardPoints ?awPo))
    ?threshold <- (Threshold (OBJECT ?thresholdOBJ &:(call ?thresholdOBJ containsCategory ?custCatOBJ))
        (from ?from &:(<= ?from ?total))(to ?to &:(>= ?to ?total))(percentage ?perc))
    =>
    (printout t "BICE AWARDA" crlf)
    (printout t "Dodati na placenu cenu racuna i zaokruzeni broj postaviti kao nove dodatne poene")
    (bind ?awardP (integer(/ (* ?perc ?total) 100)))
    (modify ?bill (awardPoints ?awardP))
    )
