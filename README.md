> [!WARNING]
> Before doing your first commit (or when you use a new machine), assure with `git config --list` that:
> 
> You set your real name with `git config --global user.name "Your name"`
> 
> Set your institutional e-mail with `git config --global user.email "name.surname@studio.unibo.it"`



Email dei componenti:

tommaso.detommaso@studio.unibo.it

matteo.giorgini12@studio.unibo.it

leonardo.grimaldi2@studio.unibo.it

edoardo.scorza@studio.unibo.it

Il gruppo si pone come obiettivo quello di realizzare un videogioco di esplorazione del mare liberamente ispirato al famoso manga "One Piece".

L'idea di base del gioco è di realizzare un mondo aperto generato casualmente diviso in sezioni. Sullo schermo sarà visibile solo una sezione per volta, ogni sezione è divisa in caselle che potranno essere occupate da vari oggetti (isole, nemici, barili). Il giocatore si muove nella mappa con una nave casella per casella. Quando si arriva al bordo di una sezione si viene portati in quella successiva. La nave potrà ingaggiare le navi nemiche, raccogliere i barili, raccogliere l'esperienza ottenuta dai combattimenti e attraccarsi alle isole, dove potrà curarsi e salvare la partita.

Funzionalità minimali ritenute obbligatorie:
* Generazione casuale del mondo
* Sistema di combattimento a turni
* Sistema di salvataggio alle isole
* Movimento delle navi nella griglia
* Sistema di interazione con barili, esperienza e isole

Funzionalità opzionali:
* Introdurre potenziamenti temporanei alla nave tramite la raccolta di barili
* Introdurre potenziamenti permanenti alla nave basati sull'esperienza ottenuta in combattimento
* Generazione di un isola finale da raggiungere per completare il gioco dopo aver esplorato almeno 10 sezioni

"Challenge" principali:
* Generazione casuale del mondo senza rischi di soft/hard lock
* Gestione equilibrate delle AI dei nemici
* Mantenere il codice estendibile

Suddivisione del lavoro:
* De Tommaso: interazione con i barili e isole; sistema di salvataggio;
* Giorgini: movimento della nave; meccaniche di combattimento;
* Grimaldi: generazione casuale delle sezioni; grafica del gioco;
* Scorza: IA dei nemici; controller;
