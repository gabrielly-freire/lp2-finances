@SuppressWarnings("module")
module br.ufrn.imd {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive jbcrypt;
    requires transitive java.sql;
    
    opens br.ufrn.imd.model to javafx.base;
    opens br.ufrn.imd.controller to javafx.fxml;
    opens br.ufrn.imd to javafx.fxml;
    exports br.ufrn.imd;

}
