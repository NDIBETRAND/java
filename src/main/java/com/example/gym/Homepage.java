package com.example.gym;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.chart.AreaChart;

import java.time.LocalDate;
import java.util.List;

public class Homepage {

    @FXML
    private AnchorPane coachpane;

    @FXML
    private TableView<Coach> coachtable;

    @FXML
    private TableColumn<Coach, String> cotabaddress;

    @FXML
    private TableColumn<Coach, String> cotabdomain;

    @FXML
    private TableColumn<Coach, String> cotabgender;

    @FXML
    private TableColumn<Coach, String> cotabname;

    @FXML
    private TableColumn<Coach, String> cotabnumber;

    @FXML
    private TextField cotxtaddress;

    @FXML
    private ComboBox<String> cotxtdomain;

    @FXML
    private ComboBox<String> cotxtgender;

    @FXML
    private TextField cotxtname;

    @FXML
    private TextField cotxtphone;

    @FXML
    private AnchorPane dashboardpane;

    @FXML
    private Label dsmonthlyexpense;

    @FXML
    private Label dsmonthlyicome;

    @FXML
    private Label dsnumberofcoaches;

    @FXML
    private Label dsnumberofmember;

    @FXML
    private AreaChart<?, ?> incomechart;

    @FXML
    private AnchorPane memberPane;

    @FXML
    private TableView<Member> membertable;

    @FXML
    private TableColumn<Member, String> memtabaddress;

    @FXML
    private TableColumn<Member, String> memtabcustomerid;

    @FXML
    private TableColumn<Member, String> memtabdomain;
    @FXML
    private TableColumn<?, ?> memtabphone;

    @FXML
    private TableColumn<?, ?> memtabstartdate;
    @FXML
    private TableColumn<?, ?> memtabendate;



    @FXML
    private TableColumn<Member, String> memtabgender;

    @FXML
    private TableColumn<Member, String> memtabname;

    @FXML
    private TableColumn<Member, String> memtabstatus;

    @FXML
    private TextField memtxtaddress;

    @FXML
    private TextField memtxtage;

    @FXML
    private TextField memtxtcustomerID;

    @FXML
    private ComboBox<String> memtxtdomain;

    @FXML
    private DatePicker memtxtenddate;

    @FXML
    private ComboBox<String> memtxtgender;

    @FXML
    private TextField memtxtname;

    @FXML
    private TextField memtxtphone;

    @FXML
    private DatePicker memtxtstartdate;

    @FXML
    private AnchorPane payment_pane;

    @FXML
    private TableView<Payment> paymenttable;

    @FXML
    private TableColumn<Payment, String> paytabcustomerid;

    @FXML
    private TableColumn<Payment, String> paytabenddate;

    @FXML
    private TableColumn<Payment, String> paytabname;

    @FXML
    private TableColumn<Payment, String> paytabstartdate;

    @FXML
    private TableColumn<Payment, String> paytabstatus;

    @FXML
    private TextField paytxtamount;

    @FXML
    private ComboBox<String> paytxtcustomerid;

    @FXML
    private ComboBox<String> paytxtname;

    @FXML
    private TableColumn<Payment, String> tabpaydate;

    @FXML
    private DatePicker paytabpaymentdate;

    @FXML
    private Label personName;

    // Observable lists for ComboBox choices and data binding
    private ObservableList<Member> members = FXCollections.observableArrayList();
    private ObservableList<Payment> payments = FXCollections.observableArrayList();
    private ObservableList<Coach> coaches = FXCollections.observableArrayList();

//    Setting welcome username
    HelloController loginpagedetails=new HelloController();
    public void welcomename(){
        personName.setText(String.valueOf(loginpagedetails.login_username.getText()));
//                (loginpagedetails.login_username.getText())
    }

    @FXML
    public void initialize() {
        // Load data from the database into observable lists
        List<Member> dbMembers = DatabaseUtils.getMembers();
        if (dbMembers != null) {
            members.setAll(dbMembers);


        }

        List<Payment> dbPayments = DatabaseUtils.getPayments();
        if (dbPayments != null) {
            payments.setAll(dbPayments);
        }

        List<Coach> dbCoaches = DatabaseUtils.getCoaches();
        if (dbCoaches != null) {
            coaches.setAll(dbCoaches);
        }

        // Bind observable lists to TableViews
        if (membertable != null) {
            membertable.setItems(members);
        }
        if (paymenttable != null) {
            paymenttable.setItems(payments);
        }
        if (coachtable != null) {
            coachtable.setItems(coaches);
        }

        // Populate default values for ComboBoxes
        ObservableList<String> genderOptions = FXCollections.observableArrayList("Male", "Female");
        ObservableList<String> domainOptions = FXCollections.observableArrayList("Fitness", "Yoga", "Martial Arts");

        if (memtxtgender != null) {
            memtxtgender.setItems(genderOptions);
        }

        if (memtxtdomain != null) {
            memtxtdomain.setItems(domainOptions);
        }

        if (cotxtdomain != null) {
            cotxtdomain.setItems(domainOptions);
        }

        if (paytxtcustomerid != null) {
            ObservableList<String> customerIDs = FXCollections.observableArrayList(
                    members.stream().map(Member::getCustomerID).toArray(String[]::new)
            );
            paytxtcustomerid.setItems(customerIDs);
        }

        // Set default payment date to today's date
        if (paytabpaymentdate != null) {
            paytabpaymentdate.setValue(LocalDate.now());
        }
    }

    // Navigation methods
    public void dashboardpaneShow() {
        showPane(dashboardpane);
        updateDashboardStats();
    }

    public void memberpaneShow() {
        showPane(memberPane);
        populateMemberTable();
    }

    public void coachpaneShow() {
        showPane(coachpane);
        populateCoachTable();
//        DatabaseUtils d= new DatabaseUtils();
//        d.getCoaches();
    }

    public void paymentpaneShow() {
        showPane(payment_pane);
    }

    private void showPane(AnchorPane paneToShow) {
        dashboardpane.setVisible(paneToShow == dashboardpane);
        memberPane.setVisible(paneToShow == memberPane);
        coachpane.setVisible(paneToShow == coachpane);
        payment_pane.setVisible(paneToShow == payment_pane);
    }

    // CRUD operations for members
    public void addMember() {
        try {
            // Validate mandatory fields before proceeding
            String customerID = memtxtcustomerID.getText();
            String name = memtxtname.getText();
            String phone = memtxtphone.getText();
            String gender = memtxtgender.getValue();
            String domain = memtxtdomain.getValue();
            LocalDate startDate = memtxtstartdate.getValue();
            LocalDate endDate = memtxtenddate.getValue();

            if (customerID.isEmpty() || name.isEmpty() || phone.isEmpty() ||
                    gender == null || domain == null || startDate == null || endDate == null) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Start date cannot be after end date.");
            }

            int age = Integer.parseInt(memtxtage.getText());
           String address   = memtxtaddress.getText();
            Member newMember = new Member(
                    customerID,
                    name,
                    phone,
                    gender,
                    age,
                    address,
                    domain,
                    startDate,
                    endDate,
                    "not paid"
            );

            DatabaseUtils.addMember(newMember);
            members.add(newMember);

//            populating the member table
            populateMemberTable();



            // Refresh the TableView to reflect the new addition
            if (membertable != null) {
                membertable.refresh();
            }

            clearMemberFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Member added successfully.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Age", "Please enter a valid number for age.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding member. Check your inputs.");
        }
    }
    public void populateMemberTable(){
        memtabcustomerid.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        memtabname.setCellValueFactory(new PropertyValueFactory<>("name"));
        memtabphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        memtabgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        memtabaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        memtabdomain.setCellValueFactory(new PropertyValueFactory<>("domain"));
        memtabstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        memtabstartdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        memtabendate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        membertable.refresh();

    }

    public void updateMember() {
        Member selectedMember = membertable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            selectedMember.setName(memtxtname.getText());
            selectedMember.setPhone(memtxtphone.getText());
            selectedMember.setAge(Integer.parseInt(memtxtage.getText()));
            selectedMember.setAddress(memtxtaddress.getText());
            selectedMember.setGender(memtxtgender.getValue());
            selectedMember.setDomain(memtxtdomain.getValue());
            selectedMember.setStartDate(memtxtstartdate.getValue());
            selectedMember.setEndDate(memtxtenddate.getValue());
            selectedMember.setCustomerID(memtxtcustomerID.getText());


            DatabaseUtils.updateMember(selectedMember);
            membertable.refresh(); // Refresh the table to reflect changes

            clearMemberFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Member updated successfully.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No member selected for updating.");
        }
    }

    public void deleteMember() {
        Member selectedMember = membertable.getSelectionModel().getSelectedItem();

        if (selectedMember == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No member selected for deletion.");
            return;
        }

        // Display a confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Are you sure you want to delete this member?");
        confirmAlert.setContentText("This action cannot be undone. Click OK to delete or Cancel to abort.");

        // Wait for the user's response
        ButtonType result = confirmAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            // User confirmed deletion
            DatabaseUtils.deleteMember(selectedMember.getCustomerID());
            members.remove(selectedMember); // Remove from observable list

            showAlert(Alert.AlertType.INFORMATION, "Success", "Member deleted successfully.");
        } else {
            // User canceled deletion
            showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Deletion cancelled.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearMemberFields() {
        memtxtname.clear();
        memtxtphone.clear();
        memtxtage.clear();
        memtxtaddress.clear();
        memtxtgender.setValue(null);
        memtxtdomain.setValue(null);
        memtxtcustomerID.clear();
        memtxtstartdate.setValue(null);
        memtxtenddate.setValue(null);
    }

    // CRUD operations for coaches
    public void addCoach() {
        try {
            // Validate mandatory fields before proceeding
            String name = cotxtname.getText();
            String phone = cotxtphone.getText();
            String gender = cotxtgender.getValue();
            String domain = cotxtdomain.getValue();
            String address = cotxtaddress.getText();


            if (name.isEmpty() || phone.isEmpty() || gender == null || domain == null || address.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            Coach newCoach = new Coach(
                    name,
                    phone,
                    gender,
                    domain,
                    address
            );

            DatabaseUtils.addCoach(newCoach);
            coaches.add(newCoach);
            populateCoachTable();


            // Refresh the TableView to reflect the new addition
            if (coachtable != null) {
                coachtable.refresh();
            }

            clearCoachFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Coach added successfully.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding coach. Check your inputs.");
        }
    }

    public void populateCoachTable(){
        cotabname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cotabnumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cotabgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cotabaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cotabdomain.setCellValueFactory(new PropertyValueFactory<>("domain"));

        coachtable.refresh();

    }


    public void updateCoach() {
        Coach selectedCoach = coachtable.getSelectionModel().getSelectedItem();
        if (selectedCoach != null) {
            selectedCoach.setName(cotxtname.getText());
            selectedCoach.setPhone(cotxtphone.getText());
            selectedCoach.setGender(cotxtgender.getValue());
            selectedCoach.setDomain(cotxtdomain.getValue());
            selectedCoach.setAddress(cotxtaddress.getText());

            DatabaseUtils.updateCoach(selectedCoach);
            coachtable.refresh(); // Refresh the table to reflect changes

            clearCoachFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Coach updated successfully.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No coach selected for updating.");
        }
    }

    public void deleteCoach() {
        Coach selectedCoach = coachtable.getSelectionModel().getSelectedItem();

        if (selectedCoach == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No coach selected for deletion.");
            return;
        }

        // Display a confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Are you sure you want to delete this coach?");
        confirmAlert.setContentText("This action cannot be undone. Click OK to delete or Cancel to abort.");

        // Wait for the user's response
        ButtonType result = confirmAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            // User confirmed deletion
            DatabaseUtils.deleteCoach(selectedCoach.getName());
            coaches.remove(selectedCoach); // Remove from observable list

            showAlert(Alert.AlertType.INFORMATION, "Success", "Coach deleted successfully.");
        } else {
            // User canceled deletion
            showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Deletion cancelled.");
        }
    }
//
//    private void showAlert(Alert.AlertType alertType, String title, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }

    public void clearCoachFields() {
        cotxtname.clear();
        cotxtphone.clear();
        cotxtaddress.clear();
        cotxtgender.setValue(null);
        cotxtdomain.setValue(null);
    }

    // Payment operations
    public void addPayment() {
        try {
            String customerID = paytxtcustomerid.getValue();
            LocalDate paymentDate = paytabpaymentdate.getValue();
            LocalDate startDate = memtxtstartdate.getValue();
            LocalDate endDate = memtxtenddate.getValue();
            String payamount=paytxtamount.getText();
            String name =paytxtname.getValue();


            int amount=Integer.parseInt(payamount);

            if (customerID == null || paymentDate == null) {
                throw new IllegalArgumentException("Invalid payment details.");
            }

            Payment payment = new Payment( customerID, amount, paymentDate);
            DatabaseUtils.addPayment(payment);

            payments.add(payment);
            populatePaymentTable();


            Member member = findMemberByCustomerID(customerID);
            if (member != null) {
                member.setStatus("paid");
                DatabaseUtils.updateMember(member);
            }

            clearPaymentFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment added successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding payment. Check your inputs.");
        }
    }
    public void populatePaymentTable(){
        paytabcustomerid.setCellValueFactory(new PropertyValueFactory<>("custmerID"));
        paytabname.setCellValueFactory(new PropertyValueFactory<>("name"));
        paytabstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        paytabstartdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        paytabenddate.setCellValueFactory(new PropertyValueFactory<>("endDate"));


        paymenttable.refresh();

    }


    private void clearPaymentFields() {
        paytxtcustomerid.setValue(null);
        paytabpaymentdate.setValue(LocalDate.now());
    }

    private Member findMemberByCustomerID(String customerID) {
        for (Member member : members) {
            if (member.getCustomerID().equals(customerID)) {
                return member;
            }
        }
        return null;
    }
    private Member findMemberByName(String name) {
        for (Member member : members) {
            if (member.getCustomerID().equals(name)) {
                return member;
            }
        }
        return null;
    }

    // Dashboard operations
    public void updateDashboardStats() {
        dsnumberofmember.setText(String.valueOf(members.size()));
        dsnumberofcoaches.setText(String.valueOf(coaches.size()));

        double totalIncome = 0;
        for (Payment payment : payments) {
            totalIncome += payment.getAmount();
        }

        dsmonthlyicome.setText(totalIncome + "FCFA");
    }
}
