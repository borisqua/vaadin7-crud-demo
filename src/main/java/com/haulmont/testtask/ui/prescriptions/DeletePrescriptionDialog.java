package com.haulmont.testtask.ui.prescriptions;

import com.haulmont.testtask.jpa.prescription.Prescription;
import com.haulmont.testtask.ui.DeleteDialog;
import com.vaadin.ui.UI;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

class DeletePrescriptionDialog extends DeleteDialog<Prescription> {
  private PrescriptionsGrid prescriptionsGrid;
  
  DeletePrescriptionDialog(String caption, UI hostUI, PrescriptionsGrid prescriptionsGrid, String titleString,
                           @Nullable Prescription entity,
                           @Nullable CrudRepository<Prescription, Long> repository) {
    super(caption, hostUI, titleString, entity, repository);
    this.prescriptionsGrid = prescriptionsGrid;
    getOKButton().addClickListener(e ->
      this.prescriptionsGrid.updateList(this.prescriptionsGrid.getPrescriptionTextFilter()));
  }
}
