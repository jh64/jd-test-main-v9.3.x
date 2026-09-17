export interface IJdTest {
  id?: number;
  name?: string;
  classification?: string;
  nodeId?: number;
  node?: string;
  code?: number | null;
  codeDescription?: string;
  parent?: IJdTest | null;
}

export const defaultValue: Readonly<IJdTest> = {};
