export interface Serbatoio {
  id: number;
  tipoCarburante: string;
  prezzoAlLitro: number;
  quantitaDisponibile: number; // Deve chiamarsi così, non livelloAttuale!
  capacitaMassima: number;
}

export interface ErogazioneRequest {
  tipoCarburante: string;
  euro: number;
}

export interface ErogazioneResponse {
  litriErogati: number;
  resto: number;
  messaggio: string;
  tipoCarburante?: string;
  importo?: number;
}