import { createContext } from "react";

export interface ModalContextTypess {
  show: boolean;
  modalType: string | null;
  openModal: (type: string) => void;
  closeModal: () => void;
}

const ModalContext = createContext<ModalContextTypess | null>(null);
