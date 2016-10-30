package seedu.taskscheduler.model.task;

import seedu.taskscheduler.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task scheduler.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    //@@author A0148145E 
    Name getName();
    TaskDateTime getStartDate();
    TaskDateTime getEndDate();
    Location getLocation();
    ReadOnlyTask copy();
    boolean isCompleted();
    TaskType getType();
    
    public enum TaskType {
        FLOATING, DEADLINE, EVENT
    }

    //@@author
    
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getLocation().equals(this.getLocation())
                && other.getTags().equals(this.getTags()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" " + getStartDate().getDisplayString())
                .append(" " + getEndDate().getDisplayString())
                .append(" " + getLocation());
        return builder.toString();
    }

    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }
    
    //@@author A0138696L
    /**  
     * For FindCommand to Formats the task as text,   
     * showing all parameters details.  
     */  
    default String getAllFieldAsText() {  
        final StringBuilder builder = new StringBuilder();  
        builder.append(getName())  
            .append(" ")  
            .append(getStartDate().getDisplayString())  
            .append(" ")  
            .append(getEndDate().getDisplayString())  
            .append(" ")  
            .append(getLocation())  
            .append(" ");  
        getTags().forEach(b -> builder.append(b.tagName + " ")); 
        builder.append(isCompleted() ? " completed" : " incomplete");
        builder.append(" " + getType());
        return builder.toString();  
    }  
    //@@author
    
    
    default boolean equals(ReadOnlyTask other) {
        return isSameStateAs(other);
    }

    //@@author A0148145E
    default TaskDateTime getComparisonDateTime() {
        if (getType() == TaskType.DEADLINE) {
            return getEndDate();
        } else if (getType() == TaskType.EVENT) {
            return getStartDate();
        }
        return null;
    }

    //@@author A0148145E
    default boolean isBefore(ReadOnlyTask other) {
        return getComparisonDateTime().isBefore(other.getComparisonDateTime());
    }

    //@@author A0148145E
    default boolean isAfter(ReadOnlyTask other) {
        return getComparisonDateTime().isAfter(other.getComparisonDateTime());
    }
}