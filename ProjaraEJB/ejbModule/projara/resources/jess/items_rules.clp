(import projara.model.items.Item)
(import projara.model.items.ItemCategory)
(import projara.model.users.*)
(import projara.model.shop.*)
(batch projara/resources/jess/model_templates.clp)

(defrule item_master
    "Nije testirano"
   	(declare (salience 10) (no-loop TRUE))
    ?item <- (Item (needOrdering ?needOrd &FALSE)
        (minQuantity ?minQ)(inStock ?inStock &:(> ?minQ ?inStock)))
    =>
    (modify ?minQ (needOrdering TRUE))
    )