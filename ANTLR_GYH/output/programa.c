// Gerado automaticamente para Linux
#include <stdio.h>
#include <stdlib.h>

int main() {
      int aux;
      int num1;
      int num3;
      int num2;
    scanf("%d", &num1);
    scanf("%d", &num2);
    scanf("%d", &num3);
    
 if (num1>num2) {
    aux = ((2+3-4+5-6)*5-1);
    num2 = (num1);
    num1 = (aux);
}
    
 if (num1>num3&&num2>3) {
    aux = (num3);
    num3 = (num1);
    num1 = (aux);
}
    
 if (num2>num3) {
    aux = (num3);
    num3 = (num2);
    num2 = (aux);
}
    printf("%d\n", num1);
    printf("%d\n", num2);
    printf("%d\n", num3);
    return 0;
}
