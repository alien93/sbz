(import projara.model.items.*)
(import projara.model.users.*)
(import projara.model.shop.*)

(deftemplate User
    (declare 
        (slot-specific TRUE)
        (from-class User)
        (include-variables TRUE)))

(deftemplate Threshold
    (declare 
        (slot-specific TRUE)
        (from-class Threshold)
        (include-variables TRUE)))

(deftemplate CustomerCategory
    (declare 
        (slot-specific TRUE)
        (from-class CustomerCategory)
        (include-variables TRUE)))

(deftemplate ActionEvent
    (declare 
        (slot-specific TRUE)
        (from-class ActionEvent)
        (include-variables TRUE)))

(deftemplate Bill
    (declare 
        (slot-specific TRUE)
        (from-class Bill)
        (include-variables TRUE)))

(deftemplate BillDiscount
    (declare 
        (slot-specific TRUE)
        (from-class BillDiscount)
        (include-variables TRUE)))

(deftemplate BillItem
    (declare 
        (slot-specific TRUE)
        (from-class BillItem)
        (include-variables TRUE)))

(deftemplate BillItemDiscount
    (declare 
        (slot-specific TRUE)
        (from-class BillItemDiscount)
        (include-variables TRUE)
        ))

(deftemplate Item
    (declare 
        (slot-specific TRUE)
        (from-class Item)
        (include-variables TRUE)))

(deftemplate ItemCategory
    (declare 
        (slot-specific TRUE)
        (from-class ItemCategory)
        (include-variables TRUE)))

(deftemplate used_regular_discount
    (declare (slot-specific TRUE))
    (slot ID (type INTEGER)))

(deftemplate Customer
    extends User
    (declare (slot-specific TRUE)
        (from-class User)
        (include-variables TRUE))
 )
