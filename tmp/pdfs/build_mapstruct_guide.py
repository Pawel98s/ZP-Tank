from reportlab.lib import colors
from reportlab.lib.colors import HexColor
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import cm
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import (SimpleDocTemplate, Paragraph, Spacer, PageBreak,
                                Table, TableStyle, KeepTogether, Flowable)
from reportlab.lib import utils
from reportlab.pdfbase.pdfmetrics import stringWidth
from pathlib import Path

OUT = Path(r"C:\Users\Pawel\Desktop\ZP Tank\output\pdf\mapstruct-entity-domain-dto-przewodnik.pdf")
OUT.parent.mkdir(parents=True, exist_ok=True)

FONT = r"C:\Windows\Fonts\arial.ttf"
FONT_BOLD = r"C:\Windows\Fonts\arialbd.ttf"
pdfmetrics.registerFont(TTFont("Arial", FONT))
pdfmetrics.registerFont(TTFont("Arial-Bold", FONT_BOLD))

NAVY = HexColor("#102A43")
BLUE = HexColor("#1F5A94")
TEAL = HexColor("#137C8B")
LIGHT_BLUE = HexColor("#EAF3FA")
LIGHT_TEAL = HexColor("#E8F5F5")
INK = HexColor("#243B53")
MUTED = HexColor("#627D98")
LINE = HexColor("#BCCCDC")
ORANGE = HexColor("#D97706")
LIGHT_ORANGE = HexColor("#FFF5E5")
RED = HexColor("#B42318")
LIGHT_RED = HexColor("#FDECEC")
GREEN = HexColor("#157347")
LIGHT_GREEN = HexColor("#EAF7EF")

styles = getSampleStyleSheet()
styles.add(ParagraphStyle(name="TitlePL", fontName="Arial-Bold", fontSize=25, leading=31,
                          textColor=NAVY, spaceAfter=10))
styles.add(ParagraphStyle(name="Subtitle", fontName="Arial", fontSize=11.5, leading=17,
                          textColor=MUTED, spaceAfter=18))
styles.add(ParagraphStyle(name="H1PL", fontName="Arial-Bold", fontSize=17, leading=22,
                          textColor=NAVY, spaceBefore=14, spaceAfter=9, keepWithNext=True))
styles.add(ParagraphStyle(name="H2PL", fontName="Arial-Bold", fontSize=12.5, leading=17,
                          textColor=BLUE, spaceBefore=10, spaceAfter=6, keepWithNext=True))
styles.add(ParagraphStyle(name="BodyPL", fontName="Arial", fontSize=9.5, leading=14,
                          textColor=INK, spaceAfter=7))
styles.add(ParagraphStyle(name="Small", fontName="Arial", fontSize=8, leading=10.5,
                          textColor=MUTED, spaceAfter=4))
styles.add(ParagraphStyle(name="CodePL", fontName="Courier", fontSize=7.35, leading=9.3,
                          textColor=HexColor("#102A43"), leftIndent=7, rightIndent=7,
                          spaceBefore=4, spaceAfter=7))
styles.add(ParagraphStyle(name="BoxTitle", fontName="Arial-Bold", fontSize=10, leading=13,
                          textColor=NAVY, spaceAfter=4))

def P(text, style="BodyPL"):
    return Paragraph(text, styles[style])

def code(text):
    escaped = text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    return Table([[Paragraph(escaped.replace("\n", "<br/>"), styles["CodePL"])]], colWidths=[17.1*cm],
                 style=TableStyle([
                     ("BACKGROUND", (0,0), (-1,-1), HexColor("#F4F7FA")),
                     ("BOX", (0,0), (-1,-1), .45, LINE),
                     ("LEFTPADDING", (0,0), (-1,-1), 4),
                     ("RIGHTPADDING", (0,0), (-1,-1), 4),
                     ("TOPPADDING", (0,0), (-1,-1), 4),
                     ("BOTTOMPADDING", (0,0), (-1,-1), 4),
                 ]))

def callout(title, body, kind="info"):
    colorset = {
        "info": (LIGHT_BLUE, BLUE), "warn": (LIGHT_ORANGE, ORANGE),
        "danger": (LIGHT_RED, RED), "good": (LIGHT_GREEN, GREEN)
    }[kind]
    return Table([[Paragraph(f"<b>{title}</b><br/>{body}", styles["BodyPL"])]], colWidths=[17.1*cm],
                 style=TableStyle([
                     ("BACKGROUND", (0,0), (-1,-1), colorset[0]),
                     ("LINEBEFORE", (0,0), (0,-1), 3, colorset[1]),
                     ("LEFTPADDING", (0,0), (-1,-1), 10),
                     ("RIGHTPADDING", (0,0), (-1,-1), 10),
                     ("TOPPADDING", (0,0), (-1,-1), 7),
                     ("BOTTOMPADDING", (0,0), (-1,-1), 7),
                 ]))

class BoxFlow(Flowable):
    def __init__(self, lines, arrows=True, color=BLUE, width=17.1*cm):
        Flowable.__init__(self); self.lines=lines; self.arrows=arrows; self.color=color; self.width=width
        self.height = len(lines)*1.12*cm - .15*cm
    def draw(self):
        c=self.canv; y=self.height-.78*cm
        for i, line in enumerate(self.lines):
            w=min(self.width-1.2*cm, max(5.0*cm, stringWidth(line, "Arial-Bold", 9)+1.0*cm))
            x=(self.width-w)/2
            c.setFillColor(HexColor("#F4F8FC")); c.setStrokeColor(self.color); c.roundRect(x,y,w,.58*cm,5,fill=1,stroke=1)
            c.setFillColor(NAVY); c.setFont("Arial-Bold",9); c.drawCentredString(self.width/2,y+.20*cm,line)
            if self.arrows and i<len(self.lines)-1:
                c.setStrokeColor(self.color); c.setFillColor(self.color); c.setLineWidth(1)
                c.line(self.width/2,y-.08*cm,self.width/2,y-.38*cm)
                c.line(self.width/2,y-.38*cm,self.width/2-.08*cm,y-.28*cm)
                c.line(self.width/2,y-.38*cm,self.width/2+.08*cm,y-.28*cm)
            y -= 1.12*cm

def footer(canvas, doc):
    canvas.saveState()
    canvas.setStrokeColor(LINE); canvas.line(1.45*cm, 1.25*cm, 19.55*cm, 1.25*cm)
    canvas.setFont("Arial", 8); canvas.setFillColor(MUTED)
    canvas.drawString(1.45*cm, .82*cm, "ZP Tank - MapStruct, Entity, Domain i DTO")
    canvas.drawRightString(19.55*cm, .82*cm, f"Strona {doc.page}")
    canvas.restoreState()

story=[]
story += [Spacer(1,1.1*cm), P("MapStruct bez pętli", "TitlePL"),
          P("Przewodnik po mapowaniu Entity ↔ Domain ↔ DTO na przykładach Client, OrderRequest, Order i DeliveryAddress z projektu ZP Tank.", "Subtitle"),
          callout("Cel", "Po lekturze będziesz umiał zaprojektować DTO, rozpoznać relację cykliczną, świadomie użyć <b>uses</b>, <b>@Named</b>, <b>qualifiedByName</b>, wariantów mapperów oraz zdecydować, kiedy <b>@Context</b> ma sens.", "good"),
          Spacer(1,.45*cm), P("Najważniejsza zasada", "H1PL"),
          P("Encje i obiekty domain mogą zawierać relacje dwukierunkowe. Pojedynczy wynik mappera nie powinien jednak rozwijać pełnej relacji w obie strony.", "BodyPL"),
          BoxFlow(["Client", "orderRequests[]", "OrderRequest.client", "ten sam Client - nie mapuj ponownie"], color=RED),
          callout("Zapamiętaj", "Nie twórz odpowiedzi ani mapowania: pełne A → pełne B → ponownie pełne A. Zamiast powrotu użyj wersji Summary, ID albo świadomie pomiń pole.", "danger"),
          Spacer(1,.35*cm), P("Spis treści", "H2PL"),
          P("1. Trzy warstwy modelu &nbsp; 2. Skąd bierze się StackOverflowError &nbsp; 3. DTO Summary/Details/Command &nbsp; 4. API mappery i uses &nbsp; 5. Entity ↔ Domain &nbsp; 6. Endpointy klienta &nbsp; 7. Zapis relacji po ID &nbsp; 8. @Context &nbsp; 9. Plan wdrożenia", "BodyPL")]

story += [PageBreak(), P("1. Trzy warstwy, trzy odpowiedzialności", "H1PL"),
          P("W projekcie te same dane przechodzą przez trzy różne reprezentacje. Nie należy oczekiwać, że będą kopiami 1:1.", "BodyPL"),
          BoxFlow(["Entity - model JPA i tabel", "Domain - logika biznesowa", "DTO - kontrakt HTTP dla frontendu"], color=TEAL),
          Table([[P("Warstwa", "BoxTitle"),P("Przykład", "BoxTitle"),P("Za co odpowiada", "BoxTitle")],
                 [P("Entity"),P("ClientEntity, OrderRequestEntity"),P("Mapowanie tabel, klucze obce, @ManyToOne, @OneToMany, lazy loading.")],
                 [P("Domain"),P("Client, OrderRequest"),P("Model używany przez serwisy i reguły biznesowe.")],
                 [P("DTO"),P("ClientSummaryDTO, CreateOrderRequestDTO"),P("Dokładny format wejścia lub odpowiedzi HTTP.")]], colWidths=[3.0*cm,5.2*cm,8.9*cm], style=TableStyle([
                     ("BACKGROUND",(0,0),(-1,0),LIGHT_BLUE),("GRID",(0,0),(-1,-1),.35,LINE),
                     ("VALIGN",(0,0),(-1,-1),"TOP"),("LEFTPADDING",(0,0),(-1,-1),6),("RIGHTPADDING",(0,0),(-1,-1),6),
                     ("TOPPADDING",(0,0),(-1,-1),6),("BOTTOMPADDING",(0,0),(-1,-1),6)
                 ])), Spacer(1,.25*cm),
          callout("Wniosek", "To, że <b>Client</b> ma listę zleceń, nie oznacza, że każdy <b>ClientDTO</b> musi ją mieć. DTO projektujesz pod konkretny ekran i endpoint.", "info"),
          P("Relacje obecne w Twoim modelu", "H2PL"),
          P("W Twoich klasach występują m.in. Client ↔ OrderRequest, Client ↔ DeliveryAddress, Order ↔ OrderRequest oraz Transport ↔ Order. Każda z nich może utworzyć rekurencję, jeśli MapStruct mapuje pola automatycznie w obu kierunkach.", "BodyPL"),
          BoxFlow(["Client -> orderRequests[] -> OrderRequest.client", "Client -> deliveryAddresses[] -> DeliveryAddress.client", "Order -> orderRequests[] -> OrderRequest.order", "Transport -> orders[] -> Order.transport"], color=ORANGE)]

story += [PageBreak(), P("2. Dlaczego pojawia się StackOverflowError", "H1PL"),
          P("MapStruct nie pobiera sam danych z bazy. Bierze otrzymany obiekt źródłowy i tworzy obiekt docelowy, wywołując mapowania dla kolejnych pól. Jeśli graf jest zamkniętym kołem, wywołania metod nigdy się nie kończą.", "BodyPL"),
          P("Przykład niebezpiecznego DTO", "H2PL"),
          code('class ClientDTO {\n    List<OrderRequestDTO> orderRequests;\n}\n\nclass OrderRequestDTO {\n    ClientDTO client;\n}'),
          BoxFlow(["ClientMapper.toDto(client)", "mapuje client.orderRequests", "mapuje OrderRequest.client", "ponownie ClientMapper.toDto(client)", "StackOverflowError"], color=RED),
          callout("Istotne", "Tak samo może się stać na poziomie Entity ↔ Domain. Błąd nie jest specyficzny dla DTO. Powstaje w każdym mapperze, który przechodzi po pełnym cyklu.", "warn"),
          P("Automatyczne mapowanie nie jest świadomym wyborem", "H2PL"),
          P("Gdy pola mają zgodne nazwy i typy, MapStruct często wygeneruje pomocnicze metody automatycznie. Jest to świetne dla klas płaskich, np. Product → ProductDTO. Przy relacjach zagnieżdżonych nie kontrolujesz wtedy, czy wygenerowana metoda nie rozwinie za dużo pól.", "BodyPL"),
          code('// Bez świadomie dobranego wariantu MapStruct może przejść\n// z ClientDTO do OrderRequestDTO, potem znów do ClientDTO.\nClientDTO toDto(Client client);')]

story += [PageBreak(), P("3. Projekt DTO: Summary, Details i Command", "H1PL"),
          P("Najlepszym rozwiązaniem jest tworzenie DTO o różnej zawartości. Nazwa powinna mówić, dla jakiego rodzaju odpowiedzi klasa istnieje.", "BodyPL"),
          P("A. DTO skrócone (Summary)", "H2PL"),
          code('public class ClientSummaryDTO {\n    private Long id;\n    private String name;\n    private String taxId;\n}\n\npublic class OrderRequestSummaryDTO {\n    private Long id;\n    private OrderRequestStatus status;\n    private ProductDTO product;\n    private BigDecimal quantity;\n    private LocalDate executionDate;\n}'),
          P("Summary nie zawiera kolekcji odwrotnych. Dzięki temu można bezpiecznie osadzić klienta w zleceniu albo zlecenie w kliencie.", "BodyPL"),
          P("B. DTO szczegółów (Details)", "H2PL"),
          code('public class OrderRequestDetailsDTO {\n    private Long id;\n    private OrderRequestStatus status;\n    private LocalDateTime createdAt;\n    private ClientSummaryDTO client;\n    private ProductDTO product;\n    private BigDecimal quantity;\n    private BigDecimal price;\n    private LocalDate executionDate;\n    private String notes;\n    private OrderSummaryDTO order;\n}'),
          P("C. DTO żądania (Command)", "H2PL"),
          code('public class CreateOrderRequestDTO {\n    private Long clientId;\n    private Long productId;\n    private BigDecimal quantity;\n    private BigDecimal price;\n    private LocalDate executionDate;\n    private String notes;\n}'),
          callout("Dobra praktyka", "Dla POST/PATCH przesyłaj ID relacji, np. <b>clientId</b> - nie <b>ClientDTO</b>. Dla GET zwróć <b>ClientSummaryDTO</b>, jeśli frontend ma wyświetlić nazwę klienta.", "good")]

story += [PageBreak(), P("4. API mappery - jak je pisać", "H1PL"),
          P("Dla jednej klasy domain możesz mieć wiele metod mapujących do różnych DTO. To jest oczekiwany styl MapStruct.", "BodyPL"),
          code('import org.mapstruct.Mapper;\nimport org.mapstruct.Named;\n\n@Mapper(componentModel = "spring")\npublic interface ClientMapper {\n\n    @Named("clientSummary")\n    ClientSummaryDTO toSummaryDto(Client client);\n\n    ClientDetailsDTO toDetailsDto(Client client);\n}'),
          P("Metoda <b>toSummaryDto</b> przepisze tylko pola obecne w ClientSummaryDTO. Ponieważ Summary nie ma orderRequests, MapStruct nie wejdzie w listę zleceń.", "BodyPL"),
          P("uses - dostęp do innych mapperów", "H2PL"),
          code('import org.mapstruct.Mapper;\nimport org.mapstruct.Mapping;\n\n@Mapper(\n    componentModel = "spring",\n    uses = {ClientMapper.class, ProductMapper.class, OrderMapper.class}\n)\npublic interface OrderRequestMapper {\n\n    @Mapping(target = "client", qualifiedByName = "clientSummary")\n    @Mapping(target = "order", qualifiedByName = "orderSummary")\n    OrderRequestDetailsDTO toDetailsDto(OrderRequest orderRequest);\n}'),
          P("<b>uses</b> oznacza: " + "&quot;MapStruct może skorzystać z metod tych mapperów.&quot; " + "<b>qualifiedByName</b> oznacza: " + "&quot;Wybierz dokładnie metodę oznaczoną tą nazwą.&quot;", "BodyPL"),
          callout("Nie myl pojęć", "Samo <b>uses</b> nie usuwa pętli. Ono tylko daje dostęp do mappera. Pętlę przerywa wybór DTO Summary, @Mapping(ignore = true) albo metody wariantowej bez relacji zwrotnej.", "warn")]

story += [PageBreak(), P("5. Entity ↔ Domain - ten sam problem, inny cel", "H1PL"),
          P("Encje i domain mogą zawierać pełne relacje, ale mapowanie ich w całości nadal może wejść w pętlę. Używaj wariantów metod zależnych od tego, co pobierasz.", "BodyPL"),
          P("A. Pobierasz pojedyncze zlecenie wraz z klientem", "H2PL"),
          BoxFlow(["OrderRequestEntity", "OrderRequest", "Client (bez orderRequests i deliveryAddresses)"], color=TEAL),
          code('@Mapper(componentModel = "spring")\npublic interface ClientEntityMapper {\n\n    @Named("clientWithoutRelations")\n    @Mapping(target = "orderRequests", ignore = true)\n    @Mapping(target = "deliveryAddresses", ignore = true)\n    Client toDomainWithoutRelations(ClientEntity entity);\n}'),
          code('@Mapper(componentModel = "spring", uses = ClientEntityMapper.class)\npublic interface OrderRequestEntityMapper {\n\n    @Mapping(target = "client", qualifiedByName = "clientWithoutRelations")\n    OrderRequest toDomain(OrderRequestEntity entity);\n}'),
          P("B. Pobierasz klienta z jego zleceniami", "H2PL"),
          BoxFlow(["ClientEntity", "Client", "orderRequests[]", "OrderRequest bez pola client"], color=TEAL),
          code('@Named("orderRequestWithoutClient")\n@Mapping(target = "client", ignore = true)\n@Mapping(target = "order", ignore = true)\nOrderRequest toDomainWithoutClient(OrderRequestEntity entity);'),
          callout("Domyślne mapowanie", "Warto mieć wariant płytki jako najczęściej używany. Warianty z kolekcjami twórz tylko wtedy, gdy konkretny przypadek użycia naprawdę ich potrzebuje.", "info")]

story += [PageBreak(), P("6. Jak wyświetlić wszystkie zlecenia klienta", "H1PL"),
          P("Przerwanie cyklu nie blokuje pobierania listy zleceń. Nie trzeba najpierw pobierać pełnego klienta i przechodzić po jego grafie. Zwykle lepiej wykonać bezpośrednie zapytanie po clientId.", "BodyPL"),
          P("Endpoint", "H2PL"),
          code('GET /clients/{clientId}/order-requests'),
          BoxFlow(["ClientController", "OrderRequestService.findByClientId(clientId)", "OrderRequestRepository.findByClientId(clientId)", "OrderRequestEntity -> OrderRequest bez client", "OrderRequestSummaryDTO[] -> JSON"], color=BLUE),
          code('public interface OrderRequestJpaRepository\n        extends JpaRepository<OrderRequestEntity, Long> {\n\n    List<OrderRequestEntity> findByClientId(Long clientId);\n}'),
          P("Frontend podał już clientId w URL, dlatego każde zlecenie na liście nie potrzebuje ponownie pełnego klienta.", "BodyPL"),
          code('[\n  {"id": 101, "status": "PENDING", "quantity": 24000},\n  {"id": 102, "status": "ACCEPTED", "quantity": 12000}\n]'),
          callout("Alternatywa", "GET /clients/{id} może zwracać ClientDetailsDTO z List&lt;OrderRequestSummaryDTO&gt;. To nadal jest bezpieczne, ponieważ OrderRequestSummaryDTO nie zawiera ClientDetailsDTO.", "good")]

story += [PageBreak(), P("7. Zapis relacji - ID w DTO, encja w serwisie", "H1PL"),
          P("MapStruct nie powinien wyszukiwać ClientEntity w repozytorium na podstawie clientId. Mapowanie danych i pobieranie danych z bazy to dwie różne odpowiedzialności.", "BodyPL"),
          BoxFlow(["POST /order-requests z clientId", "Serwis waliduje i pobiera ClientEntity", "Mapper tworzy OrderRequestEntity bez relacji", "Serwis ustawia entity.client", "Repository.save(entity)"], color=GREEN),
          code('ClientEntity client = clientJpaRepository.getReferenceById(request.getClientId());\nProductEntity product = productJpaRepository.getReferenceById(request.getProductId());\n\nOrderRequestEntity entity = orderRequestEntityMapper\n        .toEntityWithoutRelations(domain);\n\nentity.setClient(client);\nentity.setProduct(product);\norderRequestJpaRepository.save(entity);'),
          P("Jeżeli potrzebujesz własnego komunikatu &quot;klient nie istnieje&quot;, użyj findById(...).orElseThrow(...). getReferenceById(...) jest przydatne, gdy wystarczy referencja po ID.", "BodyPL"),
          callout("Właściciel relacji", "W JPA zwykle strona z @ManyToOne i @JoinColumn przechowuje klucz obcy. Przykładowo OrderRequestEntity.client przechowuje client_id. Przy zapisie ustawiasz tę stronę; kolekcję ClientEntity.orderRequests zazwyczaj ignorujesz w domyślnym mapperze.", "info")]

story += [PageBreak(), P("8. @Context - kiedy używać, a kiedy nie", "H1PL"),
          P("@Context przekazuje podczas mapowania obiekt pamięci podręcznej. Dzięki IdentityHashMap można zwrócić wcześniej zmapowaną instancję zamiast mapować ją kolejny raz.", "BodyPL"),
          code('public class CycleAvoidingMappingContext {\n    private final Map<Object, Object> knownInstances = new IdentityHashMap<>();\n\n    @BeforeMapping\n    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {\n        return (T) knownInstances.get(source);\n    }\n\n    @BeforeMapping\n    public void storeMappedInstance(Object source, @MappingTarget Object target) {\n        knownInstances.put(source, target);\n    }\n}'),
          code('Client toDomain(\n    ClientEntity entity,\n    @Context CycleAvoidingMappingContext context\n);'),
          P("Wszystkie zagnieżdżone metody, które mają korzystać z tego zabezpieczenia, muszą przyjmować ten sam parametr @Context. Przy każdym najwyższym wywołaniu tworzysz nowy context.", "BodyPL"),
          callout("Ograniczenie", "@Context zatrzymuje rekurencję MapStruct, ale wynik nadal może być prawdziwie cykliczny: Client → OrderRequest → ten sam Client. Jackson przy serializacji JSON może wtedy nadal mieć problem.", "warn"),
          callout("Ograniczenie w Twoim Domain", "Twoje klasy domain są budowane przez Lombok @Builder i nie mają setterów. Standardowy context najłatwiej działa z mutowalnymi obiektami, które można zapamiętać przed zmapowaniem pól zagnieżdżonych. Z builderami wymaga to dodatkowego projektu lub ręcznego składania grafu.", "danger"),
          P("Wniosek: @Context jest narzędziem dla pełnych grafów używanych wewnętrznie. Dla REST API nadal wybieraj DTO bez cykli.", "BodyPL")]

story += [PageBreak(), P("9. Praktyczny plan dla ZP Tank", "H1PL"),
          P("Nie musisz przebudować wszystkiego jednego dnia. Najpierw napraw ścieżki, które wywołują błąd, a potem konsekwentnie wprowadzaj wzorzec.", "BodyPL"),
          Table([[P("Krok", "BoxTitle"),P("Co zrobić", "BoxTitle"),P("Dlaczego", "BoxTitle")],
                 [P("1"),P("Dodaj ClientSummaryDTO, OrderRequestSummaryDTO, OrderSummaryDTO."),P("Bezpieczne osadzanie danych w innych odpowiedziach.")],
                 [P("2"),P("Zmień OrderRequestDTO.client z ClientDTO na ClientSummaryDTO."),P("Koniec cyklu z Client.orderRequests.")],
                 [P("3"),P("Zmień OrderRequestDTO.order na OrderSummaryDTO."),P("Koniec cyklu Order.orderRequests.")],
                 [P("4"),P("Usuń pełnego ClientDTO z DeliveryAddressDTO lub zastąp clientId/ClientSummaryDTO."),P("Koniec cyklu adres - klient.")],
                 [P("5"),P("Dodaj @Named i uses dla zagnieżdżonych mapperów."),P("Wybierasz świadomie wariant mapowania.")],
                 [P("6"),P("W Entity mapperach dodaj warianty withoutRelations / withoutClient."),P("Zabezpieczasz Entity ↔ Domain.")],
                 [P("7"),P("Dodaj Create/Update DTO z clientId, productId itd."),P("Relacje ustawiasz w serwisie, nie przez pełny graf.")],
                 [P("8"),P("Dodaj endpoint /clients/{id}/order-requests."),P("Lista zleceń bez rozwijania całego klienta.")]], colWidths=[1.2*cm,8.3*cm,7.6*cm], style=TableStyle([
                     ("BACKGROUND",(0,0),(-1,0),LIGHT_TEAL),("GRID",(0,0),(-1,-1),.35,LINE),("VALIGN",(0,0),(-1,-1),"TOP"),
                     ("LEFTPADDING",(0,0),(-1,-1),6),("RIGHTPADDING",(0,0),(-1,-1),6),("TOPPADDING",(0,0),(-1,-1),6),("BOTTOMPADDING",(0,0),(-1,-1),6)
                 ])),
          Spacer(1,.35*cm), P("Checklist przed dodaniem nowej relacji", "H2PL"),
          P("• Czy DTO A zawiera DTO B? &nbsp;&nbsp; • Czy DTO B może z powrotem zawierać pełne DTO A? &nbsp;&nbsp; • Czy do odpowiedzi wystarczy SummaryDTO? &nbsp;&nbsp; • Czy do zapisu wystarczy ID? &nbsp;&nbsp; • Czy mapper Entity ↔ Domain ma wariant bez relacji zwrotnej?", "BodyPL"),
          callout("Ostatnia zasada", "Projektuj odpowiedź HTTP od potrzeb ekranu, a nie od struktury tabel. Encje mogą być bogate i połączone; DTO powinny być celowe, małe oraz wolne od przypadkowych cykli.", "good")]

doc=SimpleDocTemplate(str(OUT), pagesize=A4, rightMargin=1.45*cm, leftMargin=1.45*cm, topMargin=1.35*cm, bottomMargin=1.65*cm)
doc.build(story, onFirstPage=footer, onLaterPages=footer)
print(OUT)
