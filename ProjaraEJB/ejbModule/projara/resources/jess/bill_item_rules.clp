(import projara.model.items.Item)
(import projara.model.items.ItemCategory)
(import projara.model.users.*)
(import projara.model.shop.*)
(batch projara/resources/jess/model_templates.clp)

(defrule vise_od_20_artikala
    "10% osnovnog popusta na vise od 20 artikala koji ne pripadaju kategoriji siroke potrosnje"
    (declare (salience 10) (no-loop TRUE))
    ?itemCategory <- (ItemCategory (name ?catName &:(eq ?catName "Široka potrošnja"))(OBJECT ?catObj))
    ?item <- (Item (OBJECT ?objectItem &:(eq FALSE (call ?objectItem isCategoryOf ?catObj))))
    ?billItem <- (BillItem (quantity ?q &:(< 20 ?q))(item ?it &:(call ?it equals ?objectItem)))
    =>
    (printout t "10% popusta na vise od 20 artikala " ?it.name crlf)
    (printout t ?objectItem crlf)
    (printout t "IZBACI PRAVILA ZA OSNOVNI POPUST" crlf)
    (bind ?newOb (new BillItemDiscount 10.0 "R" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (assert (used_regular_discount (ID (call ?billItem.OBJECT getItemNo))))
    )

(defrule ukupna_vrednost_preko_5000
	"7% osnovnog popusta na 7% za stavku preko 5000 i kategorija siroke potrosnje"
    (declare (salience 10) (no-loop TRUE))
    ?itemCategory <- (ItemCategory (name ?catName &:(eq ?catName "Široka potrošnja"))(OBJECT ?catObj))
    ?item <- (Item (OBJECT ?itObj &:(call ?itObj isCategoryOf ?catObj)))
    (used_regular_discount (ID ?urdId))
    ?billItem <- (BillItem (OBJECT ?biItObj &:(neq ?urdId (call ?biItObj getItemNo)))(price ?priceBillItem &:(> ?priceBillItem 5000))(item ?bIt &:(call ?bIt equals ?itObj)))
    =>
    (printout t "7% popusta na stavku")
    (printout t ?item.name crlf)
    (bind ?newOb (new BillItemDiscount 7.0 "R" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (assert (used_regular_discount (ID (call ?billItem.OBJECT getItemNo))))
    )

(defrule vise_od_5_artikala
    "5% osnovnog popusta na vise od 5 artikala iz kategorije televizori, racunari laptopovi"
    (declare (salience 8)(no-loop TRUE))
    ?itemCategory <- (ItemCategory (name ?catName &:(eq ?catName "Televizori, računari, laptopovi"))(OBJECT ?catObj))
    ?item <- (Item (OBJECT ?itObj &:(call ?itObj isCategoryOf ?catObj)))
    (used_regular_discount (ID ?urdId))
    ?billItem <- (BillItem (OBJECT ?biItObj &:(neq ?urdId (call ?biItObj getItemNo)))(quantity ?q &:(> ?q 5))(item ?bIt &:(call ?bIt equals ?itObj)))   
    =>
    (printout t "5% popusta na 5 artikala iz kategorije televizori" crlf)
    (printout t ?item.name crlf)
    (bind ?newOb (new BillItemDiscount 5.0 "R" ?billItem.OBJECT))
    (definstance BillItemDiscount ?newOb)
    (assert (used_regular_discount (ID (call ?billItem.OBJECT getItemNo))))
    )


