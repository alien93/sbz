package projara.test;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import jess.JessException;
import jess.Rete;

@Singleton
@Remote(TestJessRemote.class)
@Startup
public class TestJessBean implements TestJessRemote {

	@Override
	@PostConstruct
	public void testJess() {
		Rete engine = new Rete();
		try {
			engine.eval("(printout t \"Hello Projara!!!!\" crlf)");
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
