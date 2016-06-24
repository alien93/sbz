(import projara.model.items.Item)
(import projara.model.items.ItemCategory)
(import projara.model.users.*)
(import projara.model.shop.*)
(batch projara/resources/jess/model_templates.clp)
(import java.util.HashSet)

(watch all)

; Dodatni popust od 3% na ceo račun ukoliko ukupna vrednost naručenih artikala 
;prelazi 50000 dinara i ukoliko u računu postoje barem 10 artikala čija ukupna 
;cena prelazi 50% cene ukupne vrednosti naručenih artikala. 

(defrule vrednost_preko_50000
    "k pravilo"
    (declare (no-loop TRUE))
   	?bill <- (Bill (originalTotal ?origTotal &:(> ?origTotal 50000))(OBJECT ?objectBill &:(eq TRUE (call ?objectBill itemsPercentageTest))))
    =>
    (printout t "prosao: " ?bill.originalTotal crlf)
    (definstance BillDiscount (new BillDiscount 3.0 "R" ?bill.OBJECT))
    (modify ?bill(discountPercentage (+ ?bill.discountPercentage 3.0)))
)

(printout t crlf "Kreiraj racun" crlf)

;kreiranje hashset-a (sada se kreira u konstruktoru klase Bill, samo za test)
/*(bind ?item (new Item "Banana" 100.0 1000))
(bind ?billItem1 (new BillItem 10.0 2 ?item))

(bind ?hs (new HashSet))
(call ?hs add ?billItem1)
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
(call ?hs add ?billItem1) 
*/


;public Bill(double total, Set<BillItem> items)
(bind ?fa (definstance Bill (new Bill 51000.0)))

(run)