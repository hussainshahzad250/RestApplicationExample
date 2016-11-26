package TransporterBankDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.truxapiv2.model.TransporterBankDetails;
import com.truxapiv2.service.TransporterBankDetailsService;

public class TransporterBankDetailsController {

	@Autowired
	private TransporterBankDetailsService  transporterBankDetailsService;
	
	@RequestMapping(value= "/bankdetail/add", method = RequestMethod.POST)
	public String addDetail(@ModelAttribute("details") TransporterBankDetails p){

		
		if(p.getId() == 0){
			this.transporterBankDetailsService.addBankDetails(p);
		}else{
			this.transporterBankDetailsService.updateBankDetails(p);
		}
		return "success";
		
	}
}
