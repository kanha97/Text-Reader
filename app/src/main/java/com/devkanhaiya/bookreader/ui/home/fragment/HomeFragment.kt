package com.devkanhaiya.bookreader.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.annotation.NonNull
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeHomeFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import com.devkanhaiya.bookreader.ui.home.adapter.TransportAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt


class HomeFragment : BaseFragment(), TransportAdapter.ClickListener {
    var list: ArrayList<Transport?>? = null

    private val transportAdapter by lazy {
        TransportAdapter(this)
    }

    var storieslist: ArrayList<Transport?>? = null

    private val storiesAdapter by lazy {
        TransportAdapter(object : TransportAdapter.ClickListener {
            override fun addOnClick(transport: Transport) {


                val bundle = Bundle()
                bundle.putParcelable(Const.TRANSPORT, transport)
                navigator.loadActivity(
                    IsolatedActivity::class.java,
                    AccountOpenJobsFragment::class.java
                )
                    .addBundle(bundle).start()

            }

            override fun addOnDeleteClick(transport: ArrayList<Transport?>) {

            }
        })
    }
    private var mInterstitialAd: InterstitialAd? = null

    private var binding: HomeHomeFragmentBinding? = null

    override fun createLayout(): Int = R.layout.home_home_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeHomeFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        addTransportDemo()
        setUpToolBar()
        setUpTransportRecyclerView()
        setUpStoriesRecyclerView()
        animateSwipe()
    }

    private fun addTransportDemo() {
        if (!appPreferences.getBoolean("add data")) {

            val lists = arrayListOf<Transport?>()

            "".let {
                Transport(

                    "\"After Introduction we will start our story .  Are you looking for a way to level up your English? Have you tried reading, but you always get bored, or find it too hard?\\n\" +\n" +
                            "                            \"\\nThen you should try my app! Here Stories in English is a collection of multiple stories .\\n\" +\n" +
                            "                            \"Take your English to the next level today!\" +\n" +
                            "                            \"OK,If you like this story share with your friends....\"" +
                            "Lets Start....." +
                            "The Velveteen Rabbit........." +
                            " A soft and fluffy Velveteen Rabbit lived in a toybox in a Boy's room.  Each day, the Boy opened the toybox and picked up Velveteen Rabbit. And Velveteen Rabbit was happy.\n" +
                            "\n" +
                            "Then newer, brighter toys came into the toybox.  They had special tricks.  Some could move when the Boy pushed a button.  Others bounced high. \n" +
                            "\n" +
                            "Velveteen Rabbit had no special tricks or buttons. No wonder the Boy started to choose these other new toys.  \n" +
                            "\n" +
                            "The Velveteen Rabbit\n" +
                            "Thanks to Ella, 12, Windsor CO\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "At night, when the toys were back all in the toy box, the other toys talked with pride about the fine things they could do. Velveteen Rabbit was quiet.  There was not much to say.\n" +
                            "\n" +
                            "Only one other toy in the toy box was like Velveteen Rabbit.  Cowboy Horse was also a soft, fluffy toy.  But he was old.  Most of his hair was worn away.  He had only one eye left. \n" +
                            "\n" +
                            "Skin Horse\n" +
                            "Thanks to Josh, 13, Windsor CO\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "Cowboy Horse said to Velveteen Rabbit, “Soft toys like us are really the lucky ones.  We get loved the most.  And when soft toys get loved and loved, we can become Real.”\n" +
                            "\n" +
                            "“What is Real?” said Velveteen Rabbit.\n" +
                            "\n" +
                            "“Being Real is the best,\" said Skin Horse.  \"You can move when you want to move. When you are Real, if you are loved, you can show your love back.”\n" +
                            "\n" +
                            "One day Nana, who took care of the Boy, flew open the lid of the toy box.  She said in a busy tone, “Oh, dear!  That walking doggie is missing. I must find something else for the Boy!”  In a second, Velveteen Rabbit was plopped down onto the bed with the Boy.\n" +
                            "\n" +
                            "Boy and toy\n" +
                            "Thanks to Josh, 13, Windsor CO\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "This began another happy time for Velveteen Rabbit.  Each night the Boy would hold Velveteen Rabbit close in his arms.  In the morning, the Boy would show Velveteen Rabbit how to make rabbit holes under the sheets.  If the Boy went outside to a picnic, or to the park, Velveteen Rabbit would come with him, too.  \n" +
                            "\n" +
                            "After awhile, with the hugging and holding, much of Velveteen Rabbit’s fur got matted down.  Its pink nose grew less pink with all the Boy’s kisses.  But Velveteen Rabbit did not care.  It was happy.\n" +
                            "\n" +
                            "One day the Boy became sick.  His forehead got very hot.  The doctor came and went.  Nana walked back and forth in fear.  Day after day, the Boy stayed in bed.   There was nothing for Velveteen Rabbit to do but to stay in bed, too, day after day.  \n" +
                            "\n" +
                            "Then at last, the Boy got better.  Such joy in the house! The doctor said the Boy must go to the shore.  How wonderful! thought Velveteen Rabbit.  Many times the Boy had talked happily about the shore, and told of its white sands and big blue ocean.  \n" +
                            "\n" +
                            "“What about this old bunny?” Nana asked the doctor.\n" +
                            "\n" +
                            "New toys\n" +
                            "Thanks to Cooper, 12, Windsor CO\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "“That old thing?” said the doctor.  “It’s full of scarlet fever germs.  Burn it at once!  Get him a new bunny.”\n" +
                            "\n" +
                            "So Velveteen Rabbit was thrown into a sack along with the Boy's bed sheets and old clothes and a lot of junk.  The sack was carried to the backyard.  The gardener was told to burn the whole thing.\n" +
                            "\n" +
                            "But the gardener was too busy with picking the beans and peas before nightfall, so he left the sack behind.  “I will take care of it tomorrow,” he said.  The sack was not tied at the top, and Velveteen Rabbit fell out.  The next day when the gardener picked up the sack to take it away to be burned, Velveteen Rabbit was not in it.\n" +
                            "\n" +
                            "Then it started to rain.  Velveteen Rabbit was sad.  So far away from the Boy, never again to be nice and cozy together, and now soaking wet!  A tear fell from Velveteen Rabbit’s eye, over his cheek.  It plopped onto the grass.\n" +
                            "\n" +
                            "All at once, at the spot where the tear fell, a flower grew up.  Then the bud of the flower opened.  A tiny Fairy!\n" +
                            "\n" +
                            "Fairy\n" +
                            "Thanks to Ava, 12, NC\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "“Little Rabbit,” said the Fairy.  “Do you know who I am?”\n" +
                            "\n" +
                            "“I wish I did,” said Velveteen Rabbit.\n" +
                            "\n" +
                            "“I am the Fairy that takes care of toys that are well loved,” said the Fairy.  \n" +
                            "\n" +
                            "By then, Velveteen Rabbit was shabby and gray.  The boy had loved off all of its whiskers.  The pink lining in the ears had long turned grey. Its brown spots, once fresh and bright, were now faded and hard to see.\n" +
                            "\n" +
                            "“It is time now for me to make you Real,” said the Fairy.  \n" +
                            "\n" +
                            "“I think I remember Real,” said Velveteen Rabbit.  Now, what was it Cowboy Horse had said?  Ah yes.  When you are Real, you can move when you want to move.  If you are loved, you can love back.\n" +
                            "\n" +
                            "With one touch of the Fairy’s wand, Velveteen Rabbit felt different.  Tickly.  All of a sudden, each one of its two legs sewn together tight, could move!  \n" +
                            "\n" +
                            "The Velveteen Rabbit\n" +
                            "Thanks to Hugo, 12\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "A fly landed on Velveteen Rabbit’s head and it was itchy.  As quick as a wink, that foot was up at the Velveteen Rabbit’s head to scratch it off.\n" +
                            "\n" +
                            "“So this is being Real\"! “I can move when I want to move!”\n" +
                            "\n" +
                            "“I will show you some new friends,” said the Fairy.  And the Fairy took Velveteen Rabbit where several rabbits ran and hopped about.  Soon they were all great friends.\n" +
                            "\n" +
                            "The Velveteen Rabbit\n" +
                            "Thanks to Hannah, 13, Windsor CO\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "Time went by.  The Boy was back from the shore.  He was all better now. \n" +
                            "\n" +
                            "One day, the Boy went to the backyard to play.  From the trees nearby, a few rabbits hopped out.  One rabbit was brown all over, and another one was all white.  A third rabbit had brown spots, most of them faded.  That one hopped the closest to the Boy.\n" +
                            "\n" +
                            "the Velveteen Rabbit\n" +
                            "Thanks to Kendall, 12, Windsor CO\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "The Boy thought, \"Why, this rabbit looks just like my old Bunny that was lost when I was sick.  I loved that Bunny!\"\n" +
                            "\n" +
                            "What he didn't know was that it was his very own Bunny, come back to see the boy. For he was the reason the Velveteen Rabbit had become Real.",
                    "Demo English Story",
                    "The Velveteen Rabbit",
                    "14/07/2021", it, 0, false, 1
                )
            }?.let {
                lists.add(
                    it
                )
            }
            "".let {
                Transport(
                    " \"परिचयानंतर आपण आपली कथा सुरू करू.  तुम्ही स्वत: मनोरंजनकरण्याचा मार्ग शोधत आहात का? तुम्ही मराठी वाचनकथा वाचण्याचा प्रयत्न केला आहे का, पण तुम्हाला नेहमीच कंटाळा येतो, की वाचायला खूप कठीण वाटते?\" +\n" +
                            "                            \"   येथे मराठीतील कथा हा एकाधिक कथांचा संग्रह आहे.          \" +\n" +
                            "                            \"                  \\\"आनंद घेण्यासाठी वेळ काढा!\\\" ठीक आहे, जर तुम्हाला ही कथा आवडली तर तुमच्या मित्रांसोबत share करा...\\n\"" +
                            "पहिलं प्रेम ( Marathi Love Story)\n" +
                            "१० वी पर्यंत मुलांच्या शाळेत शिकलो. ११ वी - १२ वी पण छोट्या कॉलेज ला असल्यामुळे तिथे पण फार मुली नव्हत्या. त्यामुळे मुलींमध्ये गेल्यावर त्यांच्याबरोबर कसे बोलायचे कसे वागायचे ह्याचा फारसा अनुभव नव्हता.  १२ वी नंतर मोठ्या कॉलेज ला गेलो.\n" +
                            "आणि Fy च्या पहिल्याच दिवशी ती दिसली.  तिकडे एका घोळक्यात थांबली होती.\n" +
                            "तिच अतिशय सुंदर,  निरामय आणि मनमोकळं हास्य पाहून मनाला खूप छान वाटलं. जाऊन तिच्याशी बोलावंसं वाटलं.\n" +
                            "पण आम्ही इतके धीट कुठे?  मनातली इच्छा मनातच दाबली आणि सगळे मुकट्यानं लेक्चर जाऊन बसलो.\n" +
                            "तिच्याशी बोलण्याचा योग आला तो Fy च्या वार्षिक परीक्षेच्या दिवशी. एक वर्ष तिला लांबून बघण्यातच समाधान मानलं. खूप धाडसानं मी बोलायला पुढे सरसावलो आणि माझ्या तोंडातून एकदाचे शब्द फुटले\n" +
                            "\"हाय, कसे गेले पेपर? मला electronics चे दोन्ही पेपर फार अवघड गेले. \"..\n" +
                            "\"गेले गठठ्यातून, आता निकाल लागला की कळेलच कसे गेले होते ते.. बाकी?  सुट्टीत काय करणार? \".. ती बोलली.\n" +
                            "\"काही ठरवलं तर नाहीये, बघू.. \"..\n" +
                            "\"ओके, बाय, मला जायचंय.. \"\n" +
                            "\"बाय.. \" मनात इच्छा नसून पण मला बाय करावे लागले.  ... आणि स्कूटी वरून ती निघून गेली.\n" +
                            "दोघांकडेही भ्रमणध्वनी नव्हता. त्यामुळे सुट्टीभर काहीच संपर्क झाला नाही.  आक्खी सुट्टी निघून गेली... आणि परत एकदा कॉलेज चा दिवस उजाडला. आज ती दिसणार म्हणून मनातल्या मनातच मी खूश होतो.\n" +
                            "ती कुठे दिसतिये का बघायला मी गेट वरच थांबलो.   ती आली,  गाडी पार्किंग मध्ये नेताना तिनी मला बघितलं आणि मस्त स्माईल दिली.\n" +
                            "'ती आपल्याकडे बघून हसली? ', मी ह्या नशेत असतानाच ती गाडी लावून माझ्या जवळ येऊन थांबली.\n" +
                            "\"काय म्हणताय साहेब? इथे का थांबलाय? \" ह्या वेळेस तिनी सुरुवात केली.\n" +
                            "मला एकावर एक सुखद धक्के बसत होते. \"काही नाही, सहजच कोणी ओळखीचं दिसतंय का ते बघत होतो.. चल जयाचं वर्गात? \"\n" +
                            "\"हो चल, उशीर झाला आहे, लेक्चर चालू झाला असेल. \"\n" +
                            "  आणि अशाप्रकारे संभाषणाला सुरुवात झाली. ती बोलायला सुद्धा खूप मस्त होती. तिच बोलणे तासंतास ऐकावे असे वाटायचे. जसे दिवस पुढे पुढे जाऊ लागले,  तसे आमच्या मध्ये मैत्री निर्माण झाली.  इतर मुलींबरोबर पण मी अगदी बिंधास्तपणे बोलू लागलो.\n" +
                            "  मला बुद्धिबळात आवड. स्पर्धा असल्याने लेक्चर बुडवून मी त्या पत्र्याच्या आर्ट सर्कल च्या खोलीत जाऊन सराव करायला लागलो.\n" +
                            "साधारण आठवडा गेला असेल,  तिनी मला विचारलं,  \"चल,  मी पण येते बुद्धी लावायला\"\n" +
                            "\"अरे वा!, चल की\" मी एकदम आनंदानं उत्तर दिले.\n" +
                            "आणि मग त्या दिवसापासून कोणत्याच लेक्चर ला न बसता थेट आर्ट सर्कल च्या खोलीत जाऊन त्या लोकांची नाटक बघत बुद्धिबळाचा सराव करायचा, अशी आमच्या दोघांची दिनचर्या झाली.\n" +
                            "तिला बुद्धिबळामधलं खरं काहीच येत नव्हत,  तरी ति यायची.  कदाचित फक्त माझ्या सहवासात राहायचे म्हणून येत असावी अस मला वाटू लागले. मग ती खास माझ्यासाठी डबा करून आणू लागली. आमच्या मधील नात हे मैत्री पुरते उरले नाहीये ह्याची एव्हाना दोघांना जाणीव झाली होती.\n" +
                            "माझयासाठी एखादी वस्तू आवडणे परिचित होत, पण एखादी व्यक्ती आवडणे हा अनुभव नवीन आणि फार वेगळाच होता. मी तिला विचारायचं ठरविलं. त्या दिवशी ती आली, थोड्याफार गप्पा झाल्या.\n" +
                            "\"काय रे? आज इतका गप्प गप्प का? काही होतंय का तुला? \"..\n" +
                            "\"नाही गं, असच, मला काहीतरी बोलायचं होत तुझ्याशी\";\n" +
                            "\"अरे मग बोल की\"..\n" +
                            "\"असू दे गं, परत कधीतरी बोलीन.. \"\n" +
                            "माझ्या मनात काय चालू आहे हे तिनी बरोब्बर ओळखलं,  तिला पण हे हवंच होत की..\n" +
                            "\"चल आपण स्कूटी वर फिरायला जाऊयात\"..  ती बोलली. मी मुकट्यानं तिच्या मागे बसलो. तिनी गाडी पाषाण च्या त्या मस्त एरियात नेली.\n" +
                            "\"हा, तू काहीतरी बोलणार होतास.. \"\n" +
                            "तिनी दिलेला सिग्नल न ओळखायला मी इतका वेडा नव्हतो. मी मन घट्ट केलं, मोठा श्वास घेतला आणि बोलून मोकळा झालो\n" +
                            "\"मला तू खूप आवडतेस!! \"..\n" +
                            "\"क क क्क क क काय? \".. तिच्याकडून हे उत्तर आलं.\n" +
                            "\"मला जे बोलायचं होत, ते मी बोललो, आकाशवाणी एकदाच होते, चल आता मला परत सोड कॉलेज जवळ.. \" मी प्रचंड हिंमत दाखवून घाई घाई मध्ये बोललो.\n" +
                            "तिनी गाडी बाजूला घेतली, आणि माझ्या डोळ्यात डोळे घालून ती म्हटली,\n" +
                            "\"तिकडे रस्त्याकडे काय बघतोय? माझ्याकडे बघ\"..  .\n" +
                            "मी हळू हळू नजर तिच्या डोळ्यांकडे नेली.\n" +
                            "\"हा आता परत बोल मगाशी काय बोललास\"..  ती म्हणाली;\n" +
                            "\"मला तू खूप आवडतेस गं, तुझी खूप आठवण येते, तुझ्याशी बोललो नाही ना, तर करमत नाही बघ, फार विचित्र वाटत.. \"  तिच्या नजरेला टाळून मी एवढे बोललो आणि तिच्या उत्तराची वाट बघू लागलो. आपण फार मोठा घोटाळा केला अस मला वाटू लागलं.\n" +
                            "\"ए वेड्या, हे बघ,..... वरती बघ माझ्याकडे\".. मग मी नजर हळू हळू तिच्या डोळ्याकडे नेली.  \"मला पण खूप आवडतोस, मला पण तुझी खूप खूप आठवण येते रे. \".. हे बोलताना तिची नजर हळूच बाजूला दुसरीकडेच गेली होती.\n" +
                            "तिचे शब्द कानावर पडताच माझा आनंद गगनात मावेनासा झाला, तिला कडकडून मिठी मारायची माझी इच्छा झाली.  पण वेळ आणि जागा चुकीची असल्यामुळे ते राहून गेलं.\n" +
                            "गाडी परत फिरली, कॉलेज च्या गेट जवळ पोचेपर्यंतची ती ६-७ मिनिटे मी मनातल्या मनातच हसत होतो. स्वतः वरच मी प्रचंड खूश झालो होतो.  ह्या गोष्टीला propose टाकणे हे म्हणतात हेही मला माहीत नव्हत.\n" +
                            "तिनी मला कॉलेज च्या दारात सोडले,  आता मात्र आम्ही दोघही एकमेकांच्या नजरेत अगदी बिनधास्त बघत होतो.\n" +
                            "तोंडातून एकही शब्द न कढता सुद्धा आमच्यामध्ये प्रचंड मोठा संवाद चालू होता..\n" +
                            "\n" +
                            "ग कॉलेज २ ला संपले की घरी न जाता तिथेच ५ पर्यंत गप्प मारत बसायच्या, ५ ला गणिताच्या क्लास ला दोघांनी एकत्र तिच्या स्कूटीवरून जायचे, एव्हाना मी सायकल आणायची बंद केली होती. क्लास मधून घरी जाताना \"घरी पोचलीस की घरच्या फोन वर मिस कॉल दे\" अस सांगणं, बाहेर खणे पिणे, खास करून विद्यापीठातल्या त्या क्लोज कॅन्टिन ला जाऊन गप्पा मारणे   अशा काही गोष्टी अगदी नियमित चालू झाल्या.\n" +
                            "एक दिवस मी सहजच विचारलं, \"काय मग ? मी पहिलाच ना?\"..\n" +
                            "तिला बहुतेक हा प्रश्न अनपेक्षित होता; ती गोंधळली ...\n" +
                            "बराच वेळ झाला तरी तिच्या तोंडातून शब्द बाहेर येईना .. आणि मग अगदी गंमत म्हणून सहजच विचारलेल्या माझ्या प्रश्नाबद्दल मलाच चिंता वाटू लागली.\n" +
                            "बराच वेळ झाला शांतता होती. परत एकदा मीच पाऊल उचलले ..\n" +
                            "\"अग बोल ना , काय झालं? कोणी असेल तर सांग.. प्लीज लपवू नकोस \" ..\n" +
                            "आता मात्र ती खूपंच गंभीर झाली. आणि तिचे डोळे पाणावले.\n" +
                            "\"मला माफ कर वेड्या, मी तुझ्यापासून हे लपवून ठेवलं, मला एक जण आवडायचा या आधी, पण प्लीज, आता तसं काही नाहीये, आणि ह्या गोष्टीला आता बरेच दिवस झाले... मी ते विसरलिये.. ....\n" +
                            "भयाण शंतता पसरली आणि ती बराच वेळ टिकून होती. \n" +
                            "वेड्या, प्लीज काहीतरी बोल रे .. मला तू खूप आवडतोस रे, खूप प्रेम आहे माझं तुझ्यावर.  \"...\n" +
                            "मला काय बोलावे ते कळेना.. मी थोडी अधिक विचारपूस केली आणि माझ्या प्रत्येक प्रश्नाला तिनी उत्तर दिल, जणू काही मी तिला हे प्रश्न विचारीन हे तिला माहीतच असावे. प्रत्येक उत्तर माझ्या कानांमधून जाताना मला प्रचंड त्रास होत होता.\n" +
                            "तो ८ महिने बाहेर आणि ४ महिने पुण्यात असतो, भरपूर पगार ...आणि अस बरंच काही त्याच्याबद्दल ऐकायला मिळाल.   \n" +
                            "माझ्या अंगावर वीज पडावी तशी माझी अवस्था झाली , मी अगदी सहज गंमत म्हणून विचारलेल्या प्रश्नाला इतकं वेगळं वळण  मिळेल असा मी विचारही नव्हता केला. मी माझी सॅक घेतली आणि मी एक शब्दही न बोलता तडकाफडकी तिथून निघून गेलो.\n" +
                            "२-३ दिवस गेले,मी काहीच संपर्क केला नाही, तिचाही फोन आला नाही. मी माझ्या मित्राशी याबद्दल बोललो. तो आमच्या दोघांचा चांगला मित्र होता\n" +
                            "\"हे बघ वेड्या, तुला ती खरंच मनापासून आवडते ?\" तो मला धीर देत बोलला.\n" +
                            "\"हो रे? हे काय विचारणं झालं का? \"\n" +
                            "\"तिच्यावर पूर्ण विश्वास आहे ? \"\n" +
                            "\"हो आहे रे .. पण ... \"\n" +
                            "\"पण वगैरे काही नाही, आधी तिला जाऊन भेट. परत कसलाही विचार मनात आणू नकोस. तिच्यावर खूप मनापासून प्रेम कर. ती तुझीच आहे वेड्या, तुझीच आहे. \"\n" +
                            "तिला मी कॉलेज मध्ये गाठलं. एखादा नवीन ओळख झालेला माणूस ज्याप्रकारे बोलेल त्या पद्धतीनं मी तिला म्हणालो,\n" +
                            "\"हाय, काय चाल्लय ? \".. \n" +
                            "\"काही नाही विशेष.. \" तिनी पण तसंच उत्तर दिले.\n" +
                            "कॉलेज संपल्यावर आम्ही अगदी नेहमीप्रमाणे बसलो होतो, पण आज आमच्यामध्ये फार गप्पा होत नव्हत्या.\n" +
                            "\"आज फार बोर होतंय नाही\".मी उगाच काहीतरी बोलायचं म्हणून बोललो.खरं तर तिच्यासोबत बसून संपूर्ण आयुष्य काढायला पण मी तयार होतो.   \n" +
                            "\"हो ना, खरंच आज फारच बोर होतंय, क्लास ला पण वेळ आहे अजून बराच; चल आपण कुठेतरी जाऊयात. \"..\n" +
                            "\"कुठे जायचं ? मला फार काही माहीत नाही ह्या एरिअयात मधलं, प्लीज विद्यापीठ नको , तिथे जाऊन जाऊन मला कंटाळा आलाय\".. मी पण माझं मत मांडलं.\n" +
                            "\"वेड्या, माझ्या घरी येतोस ? इथे जवळच आहे.. \" चल जाऊ.. बस मागे.\n" +
                            "माझ्या उत्तराची वाट न बघताच तिनी गाडी सुरू केली , मी मख्खासारखा मागे बसलो. आणि गाडी निघाली. तिच्या भावाशी आणि आईशी आपण काय बोलणार आहोत, ह्याची मी तयारी सुरू केली.\n" +
                            "आम्ही घरी पोचलो. घरी कोणीच नव्हत. मी एक सुस्कारा सोडला. तिनी मस्त चहा आणि मॅगी करून आणले . आम्ही पहिल्यासारखे बोलत नव्हतो. आणि ह्याची जाणीव दोघांनाही होती.\n" +
                            "मॅगी खाता खाता एकमेकांना नजर भिडली, मॅगी ची प्लेट मी अलगद बाजूला ठेवली आणि तिच्या जवळ जाऊन तिला कडकडून मिठी मारली.\n" +
                            "तिला पण खूप आनंद झाला.\n" +
                            "झालं गेलं सगळं विसरायला झालं आणि आमची गाडी परत एकदा सुरळीत चालू झाल्याची जाणीव आम्हाला झाली.\n" +
                            "..\n" +
                            "कधी कधी तिच्या घरी जाण , मग तिनी मस्तपैकी चहा - आणि मॅगी बनवणे, तिच्या कुशीत पडून मस्त गप्प मारण, भविष्याची स्वप्ने रंगवणे अश्या गोष्टींनी मन अगदी प्रसन्न होत . प्रेम खरंच इतकं सुंदर असत ह्याची जाणीव झाली...\n" +
                            "काही महिने निघून गेले. सर्व काही सुरळीत चालू होत.\n" +
                            "\n" +
                            "त्यादिवशी त्यांच्या घरी असताना, अचानक फोन वाजला. तिनी उचलला. आणि ती खूप खिन्न झाली. फक्त \"ठीके, येते लगेच \" अस ती फोनवर बोलली. तिनी पटापट आवरायला घेतलं, आणि आवरता आवरताच ती मला बोलली..\n" +
                            "\"चल वेड्या, मला जायला लागेल , \"\n" +
                            "फोनवरील व्यक्ती कोण होती हे मला कळू शकले नाही,\n" +
                            "\"अग, काय झालं? अस अचानक कुठे निघालीस? कोणाचा फोन होता?\"\n" +
                            "\"तुला सांगायलाच पाहिजे का कोणाचा फोन होता ते? \" तिनी उलट प्रश्न केला..\n" +
                            "\"हो , मला सांगायलाच पाहिजे .. \" मला ते न जाणून कशी चैन पडली असती ?\n" +
                            "\"तो पुण्यात आलाय, त्यानं मला त्याच्या घरी बोलवलाय, काहीतरी काम आहे म्हणे\"..\n" +
                            "माझ्यासाठी हा प्रचंड मोठा धक्का होता. तरीपण मी स्वतः ला सावरत मन खंबिर केल, माझ्या भावना चेहर् यावर न आणण्याचा मी आटोकाट प्रयत्न केला.    \n" +
                            "\"ठीके, जा.. \" तिला नाही म्हणून मला उगाचच वाईटपणा घ्यायचा नव्हता.\n" +
                            "का कुणास ठवूक , पण आमच्या नात्याचे अस्तित्त्व आता फार काळ टिकणार नाही, अस मला उगाचच वाटले.     \n" +
                            "\n" +
                            "त्यादिवसपासून ती माझ्याशी आधीसारखी बोललीच  नाही, तिच्या डोळ्यात सतत काहीतरी लपवत असल्याची भावना मला स्पष्ट दिसायची.\n" +
                            "माझं मन ह्या प्रकारानं फार चिरडून निघत होत.        \n" +
                            "त्या दिवशी ती फार वेगळी दिसत होती, रडून रडून डोळे सुजले होते, ती बहुतेक मनाचा पूर्ण निश्चय करून आली असावी. क्लास संपल्यावर तिनी मला थांबवलं, गप्प मारत मारत आम्ही पूना हॉस्पिटल च्या पुलावर जाऊन उभे राहिलो.\n" +
                            "\"वेड्या, मला तुला काहीतरी सांगाचय रे \"..\n" +
                            "\"ह्म्म .. मला कल्पना आलिये, बोलून टाक..\" मी अतिशय घट्ट मनानं तिला बोललो. तिला असे दाखवायचा प्रयत्न केला, की ती मला काहीपण बोलली तरी मी अतिशय कठोरपणे ते पचवून घेईन.\n" +
                            " \"मी त्याच्याशिवाय नाही रे जगू शकत.. एम सॉरी.. प्लीज मला माफ कर.. प्लीज .. \"...\n" +
                            "मझी स्वतःवरच खुप चिडचिड झाली.  काहीही न बोलता मी उलटा फिरलो, माझ्या हताश, हरलेल्या मनाला सावरत घरी पोचलो. वरच्या खोलीचे दार लावून ओक्साबोक्शी रडत बसलो. ' तिनी जे काही केलं हे तिलाच नीट कळत नव्हत ' अस म्हणून मी मनातल्या मनात तिला माफ करून टाकलं, पाकिटामधून तिचा फोटो काढला, कसलाही विचार न करता मी तो फोटोचे असंख्य तुकडे केले...\n" +
                            "आमच्या प्रेमाची गाडी रुळावरून मझ्या डोळ्यान्समोर घसरली आणि मी मात्र बघतच राहिलो...\n" +
                            ".\n" +
                            ".\n" +
                            "असेच दिवस जात राहिले. \n" +
                            "परत तिच्याशी बोलण्याचा फारसा प्रयत्न केला नाही. तिला मनात खोल कुठेतरी गाडून टाकलं.\n" +
                            "अजून पण अधून मधून मेसेज आणि क्वचित कधीतरी फोन पण होतो. हे बरोबर नाहीये हे माहीत असून पण मेसेज जातोच.\n" +
                            "नंतर अनेक मुली भेटल्या, पण मनापासून कोणी आवडली नाही. मी पण शोधायचा प्रयत्न केला नाही. कधी कधी स्वतःच्या परिस्थितिवरच हसू येत. एक वेळ अशी होती की माझ्याकडे गाडी नव्हती, पैसे नव्हते पण ती होती, आणि आता माझ्याकडे गाडी आहे, पैसे आहेत, पण प्रेम करायला \"ती\" नाहिये.\n" +
                            "\n" +
                            "काही लोक म्हणतात \"पाहिलं प्रेम तेच खरं प्रेम, बाकी सगळी मजबूरी \".. सद्ध्या तरी हेच बरोबर वाटतय....",
                    "डेमो मराठी कथा",
                    "पहिलं प्रेम(Marathi Love Story)",
                    "14/07/2021", it, 2, false, 2
                )
            }?.let {
                lists.add(
                    it
                )
            }
            "".let {
                Transport(
                    "   \"हम परिचय के बाद अपनी कहानी शुरू करेंगे।  क्या आप अपने आप को मनोरंजन करने का तरीका ढूंढ रहे हैं? क्या आपने हिंदी कहानियां पढ़ने की कोशिश की है, लेकिन क्या आप हमेशा ऊब जाते हैं या पढ़ना बहुत मुश्किल लगता है? + +\\n\" +\n" +
                            "                            \"                            \\\"यहां हिंदी कहानी कई कहानियों का संग्रह है ।          \\\" +\\n\" +\n" +
                            "                            \"                            \\\"आनंद लेने के लिए समय निकालें!\\\" ठीक है, यदि आप इस कहानी को पसंद करते हैं, तो इसे अपने दोस्तों के साथ साझा करें ... \\n\"\n" +
                            "             " +
                            "सच्चे प्यार की लव स्टोरी....." +
                            "ऋषि को चौदह साल की उम्र में ही पहला प्यार हो गया था| ऋषि उस समय आठवीं क्लास में था, उम्र कम थी लेकिन मॉर्डन ज़माने में लोग इसी उम्र में प्यार कर बैठते हैं|\n" +
                            "\n" +
                            "ऋषि का ये पहला प्यार उसकी क्लास में पढ़ने वाली लड़की “नीलम” के साथ था| नीलम अमीर घराने की लड़की थी, उम्र यही कोई 13 -14 साल ही होगी और दिखने में बला की खूबसूरत थी| नीलम के पापा का प्रापर्टी डीलिंग का काम था, अच्छे पैसे वाले लोग थे|\n" +
                            "\n" +
                            "\n" +
                            " \n" +
                            "ऋषि मन ही मन नीलम को दिल दे बैठा था लेकिन हमेशा कहने से डरता था| ऋषि के पिता एक स्कूल में अध्यापक थे| उनका परिवार भी सामान्य ही था इसीलिए डर से ऋषि कभी प्यार का इजहार नहीं करता था|\n" +
                            "\n" +
                            "चलो इस प्यार के बहाने ऋषि की एक गन्दी आदत सुधर गयी| ऋषि आये दिन स्कूल ना जाने के नए नए बहाने बनाता था लेकिन आज कल टाइम से तैयार होके चुपचाप स्कूल चला आता था| माँ बाप सोचते बच्चा सुधर गया है लेकिन बेटे का दिल तो कहीं और अटक चुका था|\n" +
                            "\n" +
                            "\n" +
                            " \n" +
                            "समय ऐसे ही बीतता गया…लेकिन ऋषि की कभी प्यार का इजहार करने की हिम्मत नहीं हुई बस चोरी छिपे ही नीलम को देखा करता था| हाँ कभी -कभी उन दोनों में बात भी होती थी लेकिन पढाई के टॉपिक पर ही.. ऋषि दिल की बात ना कह पाया|\n" +
                            "\n" +
                            "समय गुजरा,,आठवीं पास की, नौवीं पास की…अब दसवीं पास कर चुके थे लेकिन चाहत अभी भी दिल में ही दबी थी|\n" +
                            "\n" +
                            "True Hindi Love Story\n" +
                            "\n" +
                            "आज स्कूल का अंतिम दिन था| ऋषि मन ही मन उदास था कि शायद अब नीलम को शायद ही देख पायेगा क्यूंकि ऋषि के पिता की इच्छा थी कि दसवीं के बाद बेटे को बड़े शहर में पढ़ाने भेजें|\n" +
                            "\n" +
                            "स्कूल के अंतिम दिन सारे दोस्त एक दूसरे से प्यार से गले मिल रहे थे, अपनी यादें शेयर कर रहे थे| नीलम भी अपनी फ्रेंड्स के साथ काफी खुश थी आज..सब एन्जॉय कर रहे थे,, अंतिम दिन जो था लेकिन ऋषि की आँखों में आंसू थे|\n" +
                            "\n" +
                            "ऋषि चुपचाप क्लास में गया और नीलम के बैग से उसका स्कूल identity card निकाल लिया| उस कार्ड पर नीलम की प्यारी सी फोटो थी| ऋषि ने सोचा कि इस फोटो को देखकर ही मैं अपने प्यार को याद किया करूंगा|\n" +
                            "\n" +
                            "बैंक से लोन लेकर पिताजी ने ऋषि को बाहर पढ़ने भेज दिया| नीलम के पिता ने भी किसी दूसरे शहर में बड़ा मकान बना लिया और वहां शिफ्ट हो गए| ऋषि अब हमेशा के लिए नीलम से जुदा हो चुका था|\n" +
                            "\n" +
                            "समय अपनी रफ़्तार से बीतता गया,, ऋषि ने अपनी पढाई पूरी की और अब एक बड़ी कम्पनी में नौकरी भी करने लगा था, अच्छी तनख्वाह भी थी लेकिन जिंदगी में एक कमी हमेशा खलती थी – वो थी नीलम।। लाख कोशिशों के बाद भी ऋषि फिर कभी नीलम से मिल नहीं पाया था|\n" +
                            "\n" +
                            "घर वालों ने ऋषि की शादी एक सुन्दर लड़की से कर दी और संयोग से उस लड़की का नाम भी नीलम ही था| ऋषि जब भी अपनी पत्नी को नीलम नाम से पुकारता उसके दिल की धड़कन तेज हो उठती थी| आखों के आगे बचपन की तस्वीरें उभर आया करतीं थी| पत्नी को उसने कभी इस बात का अहसास ना होने दिया था लेकिन आज भी नीलम से सच्चा प्यार करता था|\n" +
                            "\n" +
                            "एक दिन ऋषि कुछ फाइल्स तलाश कर रहा था कि अचानक उसे नीलम का वो बचपन का Identity Card मिल गया| उसपर छपे नीलम के प्यारे से चेहरे को देखकर ऋषि भावुक हो उठा कि तभी पत्नी अंदर आ गयी और उसने भी वह फोटो देख ली|\n" +
                            "\n" +
                            "पत्नी – यह कौन है ? जरा इसकी फोटो मुझे दिखाओ\n" +
                            "\n" +
                            "ऋषि – अरे कुछ नहीं, ये ऐसे ही बचपन में दोस्त थी\n" +
                            "\n" +
                            "पत्नी – अरे यह तो मेरी ही फोटो है, ये मेरा बचपन का फोटो है,, देखो ये लिखा “सरस्वती कान्वेंट स्कूल” यहीं तो पढ़ती थी मैं\n" +
                            "\n" +
                            "ऋषि यह सुनकर ख़ुशी से पागल सा हो गया – क्या है तुम्हारी फोटो है ? मैं इस लड़की से बचपन से बहुत प्यार करता हूँ\n" +
                            "\n" +
                            "नीलम ने अब ऋषि को अपनी पर्सनल डायरी दिखाई जहाँ नीलम की कई बचपन की फोटो लगीं थीं| ऋषि की पत्नी वास्तव में वही नीलम थी जिसे वह बचपन से प्यार करता था|\n" +
                            "\n" +
                            "नीलम ने ऋषि के आंसू पौंछे और प्यार से उसे गले लगा लिया क्यूंकि वह आज से नहीं बल्कि बचपन से ही उसका चाहने वाला था|\n" +
                            "\n" +
                            "ऋषि बार बार भगवान् का शुक्रिया अदा कर रहा था!!\n" +
                            "\n" +
                            "\n" +
                            " \n" +
                            "दोस्तों वो कहते हैं ना कि प्यार अगर सच्चा हो तो रंग लाता ही है| ठीक वही हुआ ऋषि और नीलम के साथ भी..\n" +
                            "\n",
                    "डेमो हिंदी कहानी",
                    "सच्चे प्यार की लव स्टोरी",
                    "14/07/2021", it, 1, false, 3
                )
            }?.let {
                lists.add(
                    it
                )
            }

            val listing: java.util.ArrayList<Transport?>? =
                appPreferences.getArrayList(Const.TRANSPORT_DATA)

            listing?.forEach {
                if (it != null) {
                    lists.add(it)
                }
            }
            appPreferences.saveArrayList(
                lists, Const.TRANSPORT_DATA
            )
            appPreferences.putBoolean("add data", true)
        }
    }

    private fun animateSwipe() {
        val animation: Animation =
            AlphaAnimation(2f, 0f) // Change alpha from fully visible to invisible
        animation.duration = 500 // duration - half a second
        animation.interpolator = LinearInterpolator() // do not alter animation rate
        animation.repeatCount = Animation.INFINITE // Repeat animation infinitely
        animation.repeatMode =
            Animation.REVERSE // Reverse animation at the end so the button will fade back in
        binding?.swipe?.startAnimation(animation)
        binding?.motionLayout?.setTransitionListener(object : TransitionAdapter() {

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                binding?.swipe?.clearAnimation()
            }

        })

        if (appPreferences.getBoolean(Const.LOG_IN_FIRST_STORIES)) {

            MaterialTapTargetPrompt.Builder(requireActivity())
                .setTarget(binding?.swipe)
                .setPrimaryText("Hear Stories")
                .setSecondaryText("Swipe Right to get Story & Here to Your Heart Content")
                .setPromptStateChangeListener { prompt, state ->


                }.show()


            //   appPreferences.putBoolean(Const.LOG_IN_FIRST,false)
        }

    }


    private fun setUpTransportRecyclerView() {

        list = appPreferences.getArrayList(Const.TRANSPORT_DATA)
        Log.d("TAG", "setUpTransportRecyclerView: ${list}")
        transportAdapter.setTransportList(list)
        binding!!.recyclerViewHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transportAdapter
        }
    }

    private fun setUpStoriesRecyclerView() {
        storieslist = ArrayList<Transport?>()
        storieslist!!.clear()
        storieslist?.apply {
            add(
                Transport(
                    "", "हिंदी कहानियां",
                    "हिंदी कहानियों को सुनें और आनंद लें",
                    "", "", 1, false, 1
                )
            )
            add(
                Transport(
                    "", "मराठी गोस्टी",
                    "ऐका आणि मराठी गोस्टीचा आनंद घ्या",
                    "", "", 2, false, 2
                )
            )
            add(
                Transport(
                    "", "English Stories",
                    "Here & Enjoy English Stories",
                    "", "", 3, false, 0
                )
            )
        }
        storiesAdapter.setTransportList(storieslist)
        binding!!.recyclerViewHomeStories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = storiesAdapter
        }
    }

    private fun setUpToolBar() {
        toolbar.showToolbar(true)
        (activity as HomeMainActivity).showBackSymbol(false)
        toolbar.setToolbarTitle(getString(R.string.title_home))
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun addOnClick(transport: Transport) {
        val adRequest: AdRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),
            requireContext().getString(R.string.intertitial_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(@NonNull interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    showAdd(transport)

                    mInterstitialAd?.show(requireActivity()) ?: Log.d(
                        "TAG",
                        "The interstitial ad wasn't ready yet."
                    )
                    Log.i("TAG", "onAdLoaded")
                }

                override fun onAdFailedToLoad(@NonNull loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i("TAG", loadAdError.message)
                    val bundle = Bundle()
                    bundle.putParcelable(Const.TRANSPORT, transport)
                    navigator.loadActivity(
                        IsolatedActivity::class.java,
                        OrderDetailsFragment::class.java
                    )
                        .addBundle(bundle).start()
                    mInterstitialAd = null
                }
            })


    }

    fun showAdd(transport: Transport) {
        mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.")
                val bundle = Bundle()
                bundle.putParcelable(Const.TRANSPORT, transport)
                navigator.loadActivity(
                    IsolatedActivity::class.java,
                    OrderDetailsFragment::class.java
                )
                    .addBundle(bundle).start()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterstitialAd = null
                Log.d("TAG", "The ad was shown.")
            }
        }

    }

    fun showAddonClick(transport: Transport) {
        mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.")
                val bundle = Bundle()
                bundle.putParcelable(Const.TRANSPORT, transport)
                navigator.loadActivity(
                    IsolatedActivity::class.java,
                    AccountOpenJobsFragment::class.java
                )
                    .addBundle(bundle).start()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterstitialAd = null
                Log.d("TAG", "The ad was shown.")
            }
        }

    }

    override fun addOnDeleteClick(transport: ArrayList<Transport?>) {
        appPreferences.saveArrayList(transport, Const.TRANSPORT_DATA)
        transportAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeMainActivity).showFloatingActionButton(true)
    }

    override fun onStart() {
        val list = appPreferences.getArrayList(Const.TRANSPORT_DATA)

        transportAdapter.setTransportList(list)

        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeMainActivity).showFloatingActionButton(false)

    }
}