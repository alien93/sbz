(import projara.model.items.Item)
(import projara.model.items.ItemCategory)
(import projara.model.users.*)
(import projara.model.shop.*)
(batch projara/resources/jess/model_templates.clp)

(defrule vise_od_20_artikala
    "10% osnovnog popusta na vise od 20 artikala koji ne pripadaju kategoriji siroke potrosnje"
    (declare (salience 20) (no-loop TRUE))
    ?itemCategory <- (ItemCategory (name ?catName &:(eq ?catName "Široka potrošnja"))(OBJECT ?catObj))
    ?item <- (Item (OBJECT ?objectItem &:(eq FALSE (call ?objectItem isCategoryOf ?catObj))))
    ?billItem <- (BillItem (quantity ?q &:(< 20 ?q))(item ?it &:(call ?it equals ?objectItem)))
    =>
    ;(printout t "10% popusta na vise od 20 artikala " ?it.name crlf)
    ;(printout t ?objectItem crlf)
    ;(printout t "IZBACI PRAVILA ZA OSNOVNI POPUST" crlf)
    (bind ?newOb (new BillItemDiscount 10.0 "R" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (assert (used_regular_discount (ID (call ?billItem.OBJECT getItemNo))))
    (modify ?billItem (discountPercentage (+ ?billItem.discountPercentage 10.0)))
    )

(defrule ukupna_vrednost_preko_5000
	"7% osnovnog popusta na 7% za stavku preko 5000 i kategorija siroke potrosnje"
    (declare (salience 19) (no-loop TRUE))
    ?itemCategory <- (ItemCategory (name ?catName &:(eq ?catName "Široka potrošnja"))(OBJECT ?catObj))
    ?item <- (Item (OBJECT ?itObj &:(call ?itObj isCategoryOf ?catObj)))
    ?billItem <- (BillItem (OBJECT ?biItOBJ)(originalTotal ?priceBillItem &:(> ?priceBillItem 5000))(item ?bIt &:(call ?bIt equals ?itObj)))
    (not (used_regular_discount (ID ?idRD &:(eq ?idRD (call ?biItOBJ getItemNo)))))
    =>
    ;(printout t "7% popusta na stavku")
    ;(printout t ?item.name crlf)
    (bind ?newOb (new BillItemDiscount 7.0 "R" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (assert (used_regular_discount (ID (call ?billItem.OBJECT getItemNo))))
    (modify ?billItem (discountPercentage (+ ?billItem.discountPercentage 7.0)))
    )

(defrule vise_od_5_artikala
    "5% osnovnog popusta na vise od 5 artikala iz kategorije televizori, racunari laptopovi"
    (declare (salience 8)(no-loop TRUE))
    ?itemCategory <- (ItemCategory (name ?catName &:(eq ?catName "Televizori, računari, laptopovi"))(OBJECT ?catObj))
    ?item <- (Item (OBJECT ?itObj &:(call ?itObj isCategoryOf ?catObj)))
    ?billItem <- (BillItem (OBJECT ?biItOBJ)(quantity ?q &:(> ?q 5))(item ?bIt &:(call ?bIt equals ?itObj)))   
    (not (used_regular_discount (ID ?idRD &:(eq ?idRD (call ?biItOBJ getItemNo)))))
    =>
    ;(printout t "5% popusta na 5 artikala iz kategorije televizori" crlf)
    ;(printout t ?item.name crlf)
    (bind ?newOb (new BillItemDiscount 5.0 "R" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (assert (used_regular_discount (ID (call ?billItem.OBJECT getItemNo))))
    (modify ?billItem (discountPercentage (+ ?billItem.discountPercentage 5.0)))
    )

(defrule kupljena_kategorija_30_dana
    "Ukoliko je proizvod te kategorije kupljen u prethodnih 30 dana - 1.0% dodatni popust "
    (declare (salience 18)(no-loop TRUE))
    ?billItem <- (BillItem (item ?item)(customer ?customer &:(call ?customer categoryBoughtInLast 30 ?item)))
    =>
    (printout t "BILL: " ?billItem crlf)
    (bind ?newOb (new BillItemDiscount 1.0 "A" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (modify ?billItem (discountPercentage (+ ?billItem.discountPercentage 1.0)))
    )

(defrule kupljen_do_15_dana
    "Ukoliko je proizvod kupljen u prethodnih 15 dana - 2.0% dodatni popust "
    (declare (salience 17)(no-loop TRUE))
    ?billItem <- (BillItem (item ?item)(customer ?customer &:(call ?customer itemBoughtInLast 15 ?item)))
    =>
    (printout t "BILL: " ?billItem crlf)
    (bind ?newOb (new BillItemDiscount 2.0 "A" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (modify ?billItem (discountPercentage (+ ?billItem.discountPercentage 2.0)))
    )

(defrule proizvod_na_akciji
    "Podrazumeva se da su samo one akcije koje su trenutno aktuelne pretocene u fakte"
    (declare (salience 16)(no-loop TRUE))
    ?action <- (ActionEvent (OBJECT ?actionObj))
    ?billItem <- (BillItem (item ?item &:(call ?actionObj isOnAction ?item)))
    =>
    (bind ?newOb (new BillItemDiscount ?action.discount "A" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (modify ?billItem (discountPercentage (+ ?billItem.discountPercentage ?action.discount)))
    )

(defrule ispravka_popusta
    "Samo oni ciji je popust veci od predvidjenog"
    (declare (salience 15)(no-loop TRUE))
    ?itemCategory <- (ItemCategory (OBJECT ?itemCatObj)(maxDiscount ?maxDisc))
    ?billItem <- (BillItem (item ?item &:(call ?itemCatObj equals (call ?item getCategory)))(discountPercentage ?discPerc &:(> ?discPerc ?maxDisc)))
    =>
    (modify ?billItem (discountPercentage ?maxDisc))
    )

(defrule krajnja_cena_stavke
    "Racunanje ukupne cene stavke"
    (declare (salience 14) (no-loop TRUE))
    ?billItem <- (BillItem (discountPercentage ?discPerc) (originalTotal ?total)(bill ?bill))
    =>
    (modify ?billItem (total (- ?total (/ (* ?total ?discPerc) 100))))
    )