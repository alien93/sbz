package projara.session.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.session.interfaces.BillExceptionRecoveryLocal;

@Stateless
@Local(BillExceptionRecoveryLocal.class)
public class BillExceptionRecoveryBean implements BillExceptionRecoveryLocal {


}
