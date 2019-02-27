package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.ui.DeleteDialog;
import com.vaadin.ui.UI;
import org.springframework.data.repository.CrudRepository;

class DeletePrescriptionDialog extends DeleteDialog<Prescription> {
  private PrescriptionsGrid prescriptionsGrid;
  
  DeletePrescriptionDialog(String caption, UI hostUI, PrescriptionsGrid prescriptionsGrid,
                           String labelString, Prescription entity, CrudRepository<Prescription, Long> repository) {
    super(caption, hostUI, labelString, entity, repository);
    this.prescriptionsGrid = prescriptionsGrid;
    getOKButton().addClickListener(e ->
      this.prescriptionsGrid.updateList(this.prescriptionsGrid.getPrescriptionTextFilter()));
  }
}
