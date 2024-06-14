package org.unitedinternet.cosmo.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compare NoteItems using a rank calculated from TriageStatus.rank
 * or event startDate, or last modified date.
 */
@SuppressWarnings("serial")
public class NoteItemTriageStatusComparator implements Comparator<NoteItem>, Serializable {

    boolean reverse = false;

    public NoteItemTriageStatusComparator() {
    }

    public NoteItemTriageStatusComparator(boolean reverse) {
        this.reverse = reverse;
    }

    public int compare(NoteItem note1, NoteItem note2) {
        if (note1.getUid().equals(note2.getUid())) {
            return 0;
        }

        //calculating ranks
        long rank1 = calculateRank(note1);
        long rank2 = calculateRank(note2);

        // comparing ranks
        return compareRanks(rank1, rank2);
    }

    //new method to handle various scenarios for calculating rank
    private long calculateRank(NoteItem note) {
        if (note.getTriageStatus() != null && note.getTriageStatus().getRank() != null) {
            return calculateRankFromTriageStatus(note);
        } else if (StampUtils.getBaseEventStamp(note) != null) {
            return calculateRankFromEventStartDate(note);
        } else if (note instanceof NoteOccurrence) {
            return calculateRankFromOccurrenceDate(note);
        } else {
            return calculateRankFromModifiedDate(note);
        }
    }

    private long calculateRankFromTriageStatus(NoteItem note) {
        // using triageStatusRank * 1000 to normalize to unix timestamp in milliseconds
        return note.getTriageStatus().getRank().scaleByPowerOfTen(3).longValue();
    }

    private long calculateRankFromEventStartDate(NoteItem note) {
        // Use startDate * -1
        return -1 * StampUtils.getBaseEventStamp(note).getStartDate().getTime();
    }

    private long calculateRankFromOccurrenceDate(NoteItem note) {
        // Use occurrence date * -1
        return -1 * ((NoteOccurrence) note).getOccurrenceDate().getTime();
    }

    private long calculateRankFromModifiedDate(NoteItem note) {
        // Use modified date * -1 as a last resort
        return -1 * note.getModifiedDate();
    }

    private int compareRanks(long rank1, long rank2) {
        if (rank1 > rank2) {
            return reverse ? -1 : 1;
        } else {
            return reverse ? 1 : -1;
        }
    }
}