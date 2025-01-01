package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.Wallet;
import com.ptit.EnglishExplorer.data.service.ChoiseService;
import com.ptit.EnglishExplorer.data.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/wallets")
public class WalletController extends BaseController<Wallet, Long, WalletService> {

    @Autowired
    public WalletController(WalletService service) {
        super(service);
    }

    @PostMapping("/create")
    public ResponseEntity<Wallet> createWallet() {
        // Tìm người dùng từ userId
        User user = ApplicationAuditAware.getCurrentUser();

        // Kiểm tra nếu người dùng không tồn tại
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Tạo ví mới cho người dùng với số dư mặc định là 0
        Wallet wallet = new Wallet();
        wallet.setUser(user);  // Gắn ví vào người dùng

        // Lưu ví vào cơ sở dữ liệu
        Wallet savedWallet = service.save(wallet);

        return new ResponseEntity<>(savedWallet, HttpStatus.CREATED);
    }

    // API để lấy ví của người dùng hiện tại
    @GetMapping("/my-wallet")
    public ResponseEntity<Wallet> getMyWallet() {
        // Lấy người dùng hiện tại từ ứng dụng
        User user = ApplicationAuditAware.getCurrentUser();

        // Kiểm tra nếu người dùng không tồn tại
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Lấy ví của người dùng
        Wallet wallet = service.findByUser(user.getId());  // Giả sử service có phương thức tìm ví theo người dùng

        // Kiểm tra nếu ví không tồn tại
        if (wallet == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

}
