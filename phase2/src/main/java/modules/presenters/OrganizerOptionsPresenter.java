package modules.presenters;

import modules.views.IOrganizerHomePageView;


/**
 * Presenter class for the Organizer actions
 */
public class OrganizerOptionsPresenter {
    private IOrganizerHomePageView iOrganizerHomePageView;

    /**
     * Constructor for OrganizerOptionsPresenter
     *
     * @param iOrganizerHomePageView interface for the organizer home page
     */
    public OrganizerOptionsPresenter(IOrganizerHomePageView iOrganizerHomePageView) {
        this.iOrganizerHomePageView = iOrganizerHomePageView;
    }
}


