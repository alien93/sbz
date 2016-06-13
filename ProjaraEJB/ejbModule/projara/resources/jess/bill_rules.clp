(import projara.model.items.Item)
(import projara.model.items.ItemCategory)
(import projara.model.users.*)
(import projara.model.shop.*)
(batch projara/resources/jess/model_templates.clp)

(defrule vrednost_preko_200000
    "Ukupna vrednost preko 200000"
    (declare (salience 5)(no-loop TRUE))
    ?bill <- (Bill (originalTotal ?origTotal &:(> ?origTotal 200000)))
    =>
    (printout t "IMA VISE" ?bill.originalTotal crlf)
    (bind ?newOb (new BillDiscount 5.0 "R" ?bill.OBJECT))
    (definstance BillDiscount ?newOb)
    (modify ?bill(discountPercentage (+ ?bill.discountPercentage 5.0)))
    )

(defrule stari_kupci
    "Nagradjuje stare kupce"
    (declare (salience 4)(no-loop TRUE))
    ?bill <- (Bill (customer ?cust &:(<= 2 (call ?cust getYears))))
    =>
    (bind ?newOb (new BillDiscount 1.0 "R" ?bill.OBJECT))
    (definstance BillDiscount ?newOb)
    (modify ?bill(discountPercentage (+ ?bill.discountPercentage 1.0)))
    (printout t "Stari kupac" crlf)
    )

(defrule srebrni_ili_zlatni_kupac
    "Nagradjuje kategoriju kupca"
    (declare (salience 3)(no-loop TRUE))
    ?custCat <- (CustomerCategory (name ?name &:(or "Zlatni" "Srebrni"))(OBJECT ?custCatObj))
    ?customer <- (Customer (OBJECT ?custObj)(id ?idCust)(category ?categ &:(call ?categ equals ?custCatObj)))
    ?bill <- (Bill (customer ?cust &:(call ?cust equals ?custObj)))
    =>
    (bind ?newOb (new BillDiscount 1.0 "R" ?bill.OBJECT))
    (definstance BillDiscount ?newOb)
    (modify ?bill(discountPercentage (+ ?bill.discountPercentage 1.0)))
    (printout t "Privilegovani kupac" crlf)
    )

(defrule konacna_cena_racuna
    "Umanjuje popust na racun"
    ?bill <- (Bill (discountPercentage ?discPerc)(originalTotal ?origTot))
  	=>
    (modify ?bill (total (- ?origTot (/ (* ?origTot ?discPerc) 100))))  
 )
