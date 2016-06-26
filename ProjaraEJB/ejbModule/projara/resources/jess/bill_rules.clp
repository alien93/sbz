(import projara.model.items.Item)
(import projara.model.items.ItemCategory)
(import projara.model.users.*)
(import projara.model.shop.*)
(batch projara/resources/jess/model_templates.clp)

(defrule vrednost_preko_200000
    "Ukupna vrednost preko 200000"
    (declare (salience 10)(no-loop TRUE))
    ?bill <- (Bill (originalTotal ?origTotal &:(> ?origTotal 200000)))
    =>
    (printout t "IMA VISE" ?bill.originalTotal crlf)
    (bind ?newOb (new BillDiscount 5.0 "R" ?bill.OBJECT "Ukupna vrednost preko 200000"))
    (definstance BillDiscount ?newOb)
    (modify ?bill(discountPercentage (+ ?bill.discountPercentage 5.0)))
    )

(defrule stari_kupci
    "Nagradjuje stare kupce"
    (declare (salience 9)(no-loop TRUE))
    ?bill <- (Bill (customer ?cust &:(<= 2 (call ?cust getYears))))
    =>
    (bind ?newOb (new BillDiscount 1.0 "A" ?bill.OBJECT "Verni kupci (preko 2 godine)"))
    (definstance BillDiscount ?newOb)
    (modify ?bill(discountPercentage (+ ?bill.discountPercentage 1.0)))
    (printout t "Stari kupac" crlf)
    )

(defrule srebrni_ili_zlatni_kupac
    "Nagradjuje kategoriju kupca"
    (declare (salience 8)(no-loop TRUE))
    ?custCat <- (CustomerCategory (name ?name &:(or "Zlatni" "Srebrni"))(OBJECT ?custCatObj))
    ?customer <- (Customer (OBJECT ?custObj)(id ?idCust)(category ?categ &:(call ?categ equals ?custCatObj)))
    ?bill <- (Bill (customer ?cust &:(call ?cust equals ?custObj)))
    =>
    (bind ?newOb (new BillDiscount 1.0 "A" ?bill.OBJECT "Zlatni ili srebrni kupci"))
    (definstance BillDiscount ?newOb)
    (modify ?bill(discountPercentage (+ ?bill.discountPercentage 1.0)))
    (printout t "Privilegovani kupac" crlf)
    )

(defrule vrednost_preko_50000
    "k pravilo"
    (declare (salience 7)(no-loop TRUE))
   	?bill <- (Bill (originalTotal ?origTotal &:(> ?origTotal 50000))(OBJECT ?objectBill &:(eq TRUE (call ?objectBill itemsPercentageTest))))
    =>
    (printout t "prosao: " ?bill.originalTotal crlf)
    (definstance BillDiscount (new BillDiscount 3.0 "A" ?bill.OBJECT "K pravilo"))
    (modify ?bill(discountPercentage (+ ?bill.discountPercentage 3.0)))
)

(defrule konacna_cena_racuna
    "Umanjuje popust na racun"
    (declare (salience 5)(no-loop TRUE))
    ?bill <- (Bill (discountPercentage ?discPerc)(originalTotal ?origTot))
  	=>
    (printout t ?origTot" "?discPerc crlf)
    (bind ?newDisc ?discPerc)
    (if (> ?newDisc 100) then(
            (bind ?newDisc 100)
            ))
    (modify ?bill (total (- ?origTot (/ (* ?origTot ?newDisc) 100))))  
 )

