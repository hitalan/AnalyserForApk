.class Lcom/example/crackme0201/MainActivity$1;
.super Ljava/lang/Object;
.source "MainActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/example/crackme0201/MainActivity;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/example/crackme0201/MainActivity;


# direct methods
.method constructor <init>(Lcom/example/crackme0201/MainActivity;)V
    .locals 0

    .prologue
    .line 1
    iput-object p1, p0, Lcom/example/crackme0201/MainActivity$1;->this$0:Lcom/example/crackme0201/MainActivity;

    .line 27
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .param p1, "v"    # Landroid/view/View;

    .prologue
    const/4 v3, 0x0

    .line 32
    iget-object v0, p0, Lcom/example/crackme0201/MainActivity$1;->this$0:Lcom/example/crackme0201/MainActivity;

    iget-object v1, p0, Lcom/example/crackme0201/MainActivity$1;->this$0:Lcom/example/crackme0201/MainActivity;

    # getter for: Lcom/example/crackme0201/MainActivity;->edit_userName:Landroid/widget/EditText;
    invoke-static {v1}, Lcom/example/crackme0201/MainActivity;->access$0(Lcom/example/crackme0201/MainActivity;)Landroid/widget/EditText;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-interface {v1}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/example/crackme0201/MainActivity$1;->this$0:Lcom/example/crackme0201/MainActivity;

    # getter for: Lcom/example/crackme0201/MainActivity;->edit_sn:Landroid/widget/EditText;
    invoke-static {v2}, Lcom/example/crackme0201/MainActivity;->access$1(Lcom/example/crackme0201/MainActivity;)Landroid/widget/EditText;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    # invokes: Lcom/example/crackme0201/MainActivity;->checkSN(Ljava/lang/String;Ljava/lang/String;)Z
    invoke-static {v0, v1, v2}, Lcom/example/crackme0201/MainActivity;->access$2(Lcom/example/crackme0201/MainActivity;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 34
    iget-object v0, p0, Lcom/example/crackme0201/MainActivity$1;->this$0:Lcom/example/crackme0201/MainActivity;

    const v1, 0x7f05000c

    invoke-static {v0, v1, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 44
    :goto_0
    return-void

    .line 38
    :cond_0
    iget-object v0, p0, Lcom/example/crackme0201/MainActivity$1;->this$0:Lcom/example/crackme0201/MainActivity;

    const v1, 0x7f05000d

    invoke-static {v0, v1, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 39
    iget-object v0, p0, Lcom/example/crackme0201/MainActivity$1;->this$0:Lcom/example/crackme0201/MainActivity;

    # getter for: Lcom/example/crackme0201/MainActivity;->btn_register:Landroid/widget/Button;
    invoke-static {v0}, Lcom/example/crackme0201/MainActivity;->access$3(Lcom/example/crackme0201/MainActivity;)Landroid/widget/Button;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setEnabled(Z)V

    .line 40
    iget-object v0, p0, Lcom/example/crackme0201/MainActivity$1;->this$0:Lcom/example/crackme0201/MainActivity;

    const v1, 0x7f05000b

    invoke-virtual {v0, v1}, Lcom/example/crackme0201/MainActivity;->setTitle(I)V

    goto :goto_0
.end method
