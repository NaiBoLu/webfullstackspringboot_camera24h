/* dinhg nghiax kieu du lieu chuan khong c=an chinh cho form modal context  */
// dinh nghia form modal context
export interface ModalContextType {
  show: boolean;
  modalType: string | null;
  openModal: (type: string) => void;
  closeModal: () => void;
}
