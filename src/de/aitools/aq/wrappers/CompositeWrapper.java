package de.aitools.aq.wrappers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import de.aitools.ie.articles.Article;

public class CompositeWrapper extends Wrapper {
  
	private final List<Wrapper> wrappers;
	
	public CompositeWrapper() {
	  this.wrappers = new ArrayList<>();
    this.addWrapper(new de.aitools.aq.wrappers.ABCNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.AddictingInfoWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.AdoboChroniclesWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.AllenWestRepublicWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.AmericanMilitaryNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.AmericanNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.AmericaNowWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.AmericonNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.BipartisanReportWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.BizStandardNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.BreitbartWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.BurrardStreetJournalWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.BusinessInsiderWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.CBSNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.CelebtricityWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ChicksOnTheRightWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ClashDailyWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.CNNMoneyWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.CNNPodcastWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.CNNWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ConservativeByteWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ConservativeStateWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ConservativeTribuneWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ConservativeVideosWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ConstitutionWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.DailyCurrantWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.DCClothesLineWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.DenverGuardianWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.DepartedWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.DonaldTrumpNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.EagleRisingWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.EmpireheraldWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.EmpireNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.EndingTheFedWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.FedUpWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.FoxNewsInsiderWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.FoxNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.FreedomDailyWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.GraphicsWSJWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.GristWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.GroopspeakWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.HeavierMetalWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.HuffingtonPostWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.IfYouOnlyNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.IHaveTheTruthWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.IPatriotWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ITunesWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.JoeForAmericaWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.LGBTNationWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.LiberalAmericaWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.LibertyNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.NationalReportWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.NBCNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.NCScooperWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.NewsbakeWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.NYDailyNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.NYPostWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.NYTimesWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.OccupyDemocratsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.OpposingViewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.PJMediaWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.PoliticoEU());
    this.addWrapper(new de.aitools.aq.wrappers.PoliticoWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.PolitistickWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ProudconsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ReelNewsNetworkWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.RightWingNewsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.SatiraTribuneWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.SoundcloudWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.Superstation95Wrapper());
    this.addWrapper(new de.aitools.aq.wrappers.TheBlackSphereWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.TheGuardianWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.TheNewsNerdWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.ThePoliticalInsiderWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.TheTrumpTruckWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.TMZHiphopWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.TwitchyWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.TwitterWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.UnitedMediaPublishingWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.USADailyPoliticsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.USANewsflashWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.USHeraldWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.USUncutWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.VoxWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.WashingtonPostWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.WatchThisWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.WinningDemocratsWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.WorldNewsDailyReportWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.WSJWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.YesImRightWrapper());
    this.addWrapper(new de.aitools.aq.wrappers.YourNewsWireWrapper());
  }

	public void addWrapper(final Wrapper wrapper) {
		this.wrappers.add(wrapper);
	}

  @Override
  public boolean isValidUri(final String targetUri) {
    for (final Wrapper wrapper : this.wrappers) {
      if (wrapper.isValidUri(targetUri)) { return true; }
    }
    return false;
  }
  
  protected Article parse(final Document jsoupDocument, final String uri) {
    for (final Wrapper wrapper : this.wrappers) {
      if (wrapper.isValidUri(uri)) {
        return wrapper.parse(jsoupDocument, uri);
      }
    }
    throw new IllegalArgumentException("No wrapper known for " + uri);
  }

  @Override
  protected Article parse(final Document jsoupDocument) {
    throw new UnsupportedOperationException("parse");
  }
}
